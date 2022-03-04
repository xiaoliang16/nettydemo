package com.liang.netty.demo.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerMessageHandler extends ChannelInboundHandlerAdapter {

    private int messageCount = 0;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String _msg = (String)msg;
        System.out.println("接收到消息, 内容为: " + _msg + ", 编号为: " + (++messageCount));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
