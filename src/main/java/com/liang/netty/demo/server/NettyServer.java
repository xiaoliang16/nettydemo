package com.liang.netty.demo.server;

import com.liang.netty.demo.handler.impl.AuthHandler;
import com.liang.netty.demo.handler.impl.HeartbeatHandler;
import com.liang.netty.demo.handler.impl.MessageHandler;
import com.liang.netty.demo.util.SpringUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class NettyServer {

    public void start(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new CustomMessageDecoder())
                                    .addLast("compressor", new HttpContentCompressor())
                                    .addLast("aggregator", new HttpObjectAggregator(65536))
                                    .addLast("handler", new HttpServerHandler())
                                    .addLast(new ServerMessageHandler())
                                    .addLast(new IdleStateHandler(0, 0, 30, TimeUnit.SECONDS))
                                    .addLast(new AuthHandler())
                                    .addLast(new HeartbeatHandler())
                                    .addLast(new MessageHandler());
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind().sync();
            log.info("服务器启动开始监听端口: {}", port);
            f.channel().closeFuture().sync();
        } finally {
             workerGroup.shutdownGracefully();
             bossGroup.shutdownGracefully();
        }
    }

}
