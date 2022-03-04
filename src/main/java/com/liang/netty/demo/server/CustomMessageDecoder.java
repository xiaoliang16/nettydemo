package com.liang.netty.demo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class CustomMessageDecoder extends ByteToMessageDecoder {

    private final static int HEAD_LENGTH = 4;


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < HEAD_LENGTH) {
            return;
        }

        in.markReaderIndex();

        //读取数据长度
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }

        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        //将缓冲区数据读到字节数组
        byte[] body = new byte[dataLength];
        in.readBytes(body);
        //将byte数据转化为对象
        Object msg = convertToObj(body);
        out.add(msg);
    }

    private Object convertToObj(byte[] body) {
        return new String(body, 0, body.length);
    }
}
