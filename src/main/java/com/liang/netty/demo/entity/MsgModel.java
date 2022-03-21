package com.liang.netty.demo.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

/**
 * 消息对象
 * @param
 */
@Data
public class MsgModel {

    /**
     * 消息类型
     */
    Integer type;

    /**
     * 内容实体
     */
    MsgInfo data;

    /**
     * 时间戳
     */
    DateTime dateTime;

}
