package com.liang.netty.demo.client;

import cn.hutool.core.date.DateTime;
import com.liang.netty.demo.entity.MsgInfo;
import com.liang.netty.demo.entity.MsgModel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private static final ConcurrentHashMap<ChannelId, ChannelHandlerContext> CLIENT_MAP = new ConcurrentHashMap<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CLIENT_MAP.put(ctx.channel().id(), ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
        System.out.println("服务端终止了服务");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MsgModel msgModel = (MsgModel)msg;
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setUuid("1000");
        msgInfo.setMsgData("测试发消息");
        msgInfo.setToUserId("6666");
        msgInfo.setFromUserId("5555");
        msgInfo.setSessionId("uewewew");
        msgInfo.setDateTime(new DateTime());
        msgInfo.setMsgType(1);
        msgModel.setData(msgInfo);
        msgModel.setType(1);
        msgModel.setDateTime(new DateTime());
        ctx.channel().writeAndFlush(msg);
    }

    /**
     * 心跳请求处理
     * @param ctx
     * @param obj
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)obj;
            if (IdleState.ALL_IDLE.equals(event.state())) {
                Random random = new Random();
                int num = random.nextInt(10);
                try {
                    ctx.channel().writeAndFlush("心跳信息");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 客户端给服务端发消息
     * @param channelId
     * @param msg
     */
    public void channelWrite(ChannelId channelId, String msg) {
        ChannelHandlerContext ctx = CLIENT_MAP.get(channelId);
        if (ctx == null) {
            return;
        }
        ctx.writeAndFlush(msg);
    }




}
