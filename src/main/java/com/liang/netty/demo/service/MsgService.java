package com.liang.netty.demo.service;

import io.netty.channel.ChannelHandlerContext;

public interface MsgService {

    void pong(ChannelHandlerContext ctx);
}
