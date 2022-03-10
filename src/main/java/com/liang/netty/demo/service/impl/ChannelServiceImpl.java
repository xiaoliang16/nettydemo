package com.liang.netty.demo.service.impl;

import com.liang.netty.demo.entity.ChannelData;
import com.liang.netty.demo.entity.User;
import com.liang.netty.demo.service.ChannelService;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;

@Service
@Slf4j
public class ChannelServiceImpl extends ChannelData implements ChannelService {

    @Override
    public void bindingChannel(User user, ChannelHandlerContext ctx) {
        ChannelId channelId = ctx.channel().id();
        if (contextChannel.containsKey(channelId)) {
            log.info("客户端: {} 是连接状态", channelId);
        } else {
            InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
            String clientIp = insocket.getAddress().getHostAddress();
            int clientPort = insocket.getPort();
            //保存连接
            contextChannel.put(channelId, ctx);
            log.info("客户端: {} 连接netty服务器IP: {}, 端口: {}", channelId, clientIp, clientPort);
            log.info("当前通道数量: {}", contextChannel.size());
        }

        //将用户id作为属性加入到channel
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        ctx.channel().attr(key).setIfAbsent(user.getUserId());

        String userId = user.getUserId();
        if (userChannel.containsKey(userId)) {
            log.info("客户端: {} 已绑定用户: {}", channelId, userId);
        } else {
            userChannel.put(userId, channelId);
        }
    }

    @Override
    public void receiveMsg(ChannelHandlerContext ctx, Object msg) {
         log.info("通道: {}, 接收到消息: {} ", ctx.channel().id(), msg.toString());
    }

    @Override
    public void remove(ChannelHandlerContext ctx) {
        ChannelId channelId = ctx.channel().id();
        if (contextChannel.containsKey(channelId)) {
           ChannelHandlerContext delCtx = contextChannel.get(channelId);
           AttributeKey<String> key = AttributeKey.valueOf("userId");
           if (delCtx.channel().hasAttr(key)) {
               String userId = delCtx.channel().attr(key).get();
               if (StringUtils.hasText(userId)) {
                   if (userChannel.containsKey(userId)) {
                       userChannel.remove(userId);
                   }
               }
           }

           contextChannel.remove(channelId);
           log.info("删除成功, 通道数量: {}", contextChannel.size());
        }

        ctx.close();
    }
}
