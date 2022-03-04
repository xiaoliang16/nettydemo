package com.liang.netty.demo.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CustomMessageEncoder extends MessageToByteEncoder<Object> {


    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        byte[] body = ((ByteBuf)msg).array();

        int dataLength = body.length;
        out.writeInt(dataLength);
        out.writeBytes(body);
    }

}
