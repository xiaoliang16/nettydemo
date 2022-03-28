package com.liang.netty.demo.handler.impl;

import cn.hutool.core.util.StrUtil;
import com.liang.netty.demo.entity.Auth;
import com.liang.netty.demo.entity.MsgModel;
import com.liang.netty.demo.entity.NettyIdentifier;
import com.liang.netty.demo.handler.BaseHandler;
import com.liang.netty.demo.service.ChannelService;
import com.liang.netty.demo.service.MsgService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter implements BaseHandler {

    private final MsgService msgService = getMsgService();

    private final ChannelService channelService = getChannelService();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MsgModel msgModel = (MsgModel)msg;
        Auth auth = msgModel.getAuth();
        Channel channel = ctx.channel();

        if (channel.hasAttr(NettyIdentifier.AUTH_KEY) && channel.attr(NettyIdentifier.AUTH_KEY).get()) {
            //传递下一个控制器
            ctx.fireChannelRead(msg);
        } else if (auth != null && StrUtil.isNotBlank(auth.getAccessKey()) &&
        StrUtil.isNotBlank(auth.getAppName()) && StrUtil.isNotBlank(auth.getSecretKey())) {
            //校验
        }
    }
}
