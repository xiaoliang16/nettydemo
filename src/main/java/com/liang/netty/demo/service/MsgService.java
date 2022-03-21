package com.liang.netty.demo.service;

import com.liang.netty.demo.entity.MsgModel;
import com.liang.netty.demo.entity.User;
import io.netty.channel.ChannelHandlerContext;

public interface MsgService {

    void pong(ChannelHandlerContext ctx);

    void sendResp(ChannelHandlerContext ctx, MsgModel msgModel);

    void msgError(ChannelHandlerContext ctx, MsgModel msgModel);

    void singleSend(ChannelHandlerContext ctx, MsgModel reqModel, User user);
}
