package com.liang.netty.demo.handler.impl;

import com.liang.netty.demo.entity.NettyIdentifier;
import com.liang.netty.demo.handler.BaseHandler;
import com.liang.netty.demo.service.ChannelService;
import com.liang.netty.demo.service.MsgService;
import com.liang.netty.demo.service.impl.ChannelServiceImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class MessageHandler extends ChannelInboundHandlerAdapter implements BaseHandler {

    private final MsgService msgService = getMsgService();

    private final ChannelService channelService = getChannelService();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        //在线人数统计
        ChannelServiceImpl.sysOnlineNum.incrementAndGet();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        ChannelServiceImpl.sysOnlineNum.decrementAndGet();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    /**
     * 客户端终止连接服务器会触发此函数
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        if (!channel.hasAttr(NettyIdentifier.CLEAR_KEY) || !channel.attr(NettyIdentifier.CLEAR_KEY).get()) {
            log.info("客户端终止连接: {}", ctx.channel().id());
            channelService.remove(ctx);
        } else {
            ctx.close();
        }
    }

    /**
     * 客户端发消息会触发此函数
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        channelService.receiveMsg(ctx, msg);
    }

    /**
     * 发生异常会触发此函数
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("通道： {} 发生错误，此连接被关闭", ctx.channel().id());
        channelService.remove(ctx);
    }


}
