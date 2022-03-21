package com.liang.netty.demo.service.impl;

import com.liang.netty.demo.entity.*;
import com.liang.netty.demo.service.MsgService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MsgServiceImpl extends ChannelData implements MsgService {

    @Override
    public void pong(ChannelHandlerContext ctx) {

    }

    @Override
    public void sendResp(ChannelHandlerContext ctx, MsgModel msgModel) {
        if (ctx == null || msgModel == null) {
            return;
        }
        Channel channel = ctx.channel();
        String userId = channel.attr(NettyIdentifier.USER_ID_KEY).get();
        log.info("回复， 用户id: {}, 通道: {}, 消息: {}", userId, channel, msgModel);
        ctx.writeAndFlush(msgModel);
    }

    @Override
    public void msgError(ChannelHandlerContext ctx, MsgModel msgModel) {

    }

    @Override
    public void singleSend(ChannelHandlerContext ctx, MsgModel msgModel, User user) {
       MsgInfo msgInfo = msgModel.getData();
       String toUserId = msgInfo.getToUserId();
       ChannelId channelId = userChannel.get(toUserId);
       ChannelHandlerContext context = contextChannel.get(channelId);
       sendResp(context, msgModel);
    }
}
