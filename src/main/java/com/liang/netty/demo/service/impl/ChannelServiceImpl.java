package com.liang.netty.demo.service.impl;

import com.liang.netty.demo.entity.ChannelData;
import com.liang.netty.demo.entity.User;
import com.liang.netty.demo.service.ChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.netty.channel.ChannelHandlerContext;

@Service
@Slf4j
public class ChannelServiceImpl extends ChannelData implements ChannelService {

    @Override
    public void bindingChannel(User user, ChannelHandlerContext ctx) {

    }

    @Override
    public void receiveMsg(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public void remove(ChannelHandlerContext ctx) {

    }
}
