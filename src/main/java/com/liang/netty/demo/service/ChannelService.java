package com.liang.netty.demo.service;

import com.liang.netty.demo.entity.User;
import io.netty.channel.ChannelHandlerContext;

public interface ChannelService {

    void bindingChannel(User user, ChannelHandlerContext ctx);

    void receiveMsg(ChannelHandlerContext ctx, Object msg);

    void remove(ChannelHandlerContext ctx);
}
