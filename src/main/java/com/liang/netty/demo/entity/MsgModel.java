package com.liang.netty.demo.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * 消息对象
 * @param <T>
 */
@Data
public class MsgModel<T> {

    /**
     * 消息类型
     */
    Integer type;

    /**
     * 内容实体
     */
    T data;

    /**
     * 时间戳
     */
    DateTime dateTime;

}
