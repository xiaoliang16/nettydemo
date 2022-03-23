package com.liang.netty.demo.entity;

import io.netty.util.AttributeKey;

/**
 * netty标识
 */
public class NettyIdentifier {

    /**
     * 心跳上限
     */
    public static final AttributeKey<Integer> TIMEOUT_NUM = AttributeKey.valueOf("timeoutNum");

    /**
     * 是否清除
     */
    public static final AttributeKey<Boolean> CLEAR_KEY = AttributeKey.valueOf("clear");

    /**
     * 用户ID
     */
    public static final AttributeKey<String> USER_ID_KEY = AttributeKey.valueOf("userId");

}
