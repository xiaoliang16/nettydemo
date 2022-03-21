package com.liang.netty.demo.handler.impl;

import com.liang.netty.demo.handler.BaseHandler;
import com.liang.netty.demo.service.MsgService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class MessageHandler extends ChannelInboundHandlerAdapter implements BaseHandler {

    private final MsgService msgService = getMsgService();


}
