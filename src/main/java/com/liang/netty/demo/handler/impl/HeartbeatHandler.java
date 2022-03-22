package com.liang.netty.demo.handler.impl;

import com.liang.netty.demo.entity.MsgModel;
import com.liang.netty.demo.handler.BaseHandler;
import com.liang.netty.demo.service.ChannelService;
import com.liang.netty.demo.service.MsgService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class HeartbeatHandler extends ChannelInboundHandlerAdapter implements BaseHandler {

    private final MsgService msgService = getMsgService();

    private final ChannelService channelService = getChannelService();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MsgModel msgModel = (MsgModel)msg;

        if (msgModel.getType() == 1) {
            log.info("心跳控制器加载客户端报文: {}", msg);
            msgService.pong(ctx);
        } else {
            //传递下一个控制器
            ctx.fireChannelRead(msg);
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        String socketString = ctx.channel().remoteAddress().toString();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("Client: {} READ_IDLE 读超时", socketString);
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("Client: {} WRITE_IDLE 写超时", socketString);
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("Client: {} ALL_IDLE 超时", socketString);
                channelService.remove(ctx);
            }
        }
    }


}
