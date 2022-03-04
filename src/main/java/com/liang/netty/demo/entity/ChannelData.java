package com.liang.netty.demo.entity;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class ChannelData {

    /**
     * 当前系统在线人数
     */
    public static AtomicInteger sysOnlineNum = new AtomicInteger();

    /**
     * 通道和context的关系表
     */
    public static final ConcurrentHashMap<ChannelId, ChannelHandlerContext> contextChannel = new ConcurrentHashMap<>();

    /**
     * 用户id和通道的关系表
     */
    public static final ConcurrentHashMap<String, ChannelId> userChannel = new ConcurrentHashMap<>();


}
