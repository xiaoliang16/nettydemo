package com.liang.netty.demo;

import com.liang.netty.demo.server.NettyServer;
import com.liang.netty.demo.service.impl.ChannelServiceImpl;
import com.liang.netty.demo.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyServiceApplication implements CommandLineRunner {

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(NettyServiceApplication.class, args);
        SpringUtil.getBean(ChannelServiceImpl.class);
    }

    @Override
    public void run(String... args) throws Exception {
        nettyServer.start(8088);
    }
}
