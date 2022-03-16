package com.liang.netty.demo.service.impl;

import com.liang.netty.demo.entity.MsgModel;
import com.liang.netty.demo.entity.NettyIdentifier;
import com.liang.netty.demo.service.MsgService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MsgServiceImpl implements MsgService {

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
}
