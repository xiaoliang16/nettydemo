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
    private Integer type;

    /**
     * 内容实体
     */
    private MsgInfo data;

    /**
     * 时间戳
     */
    private DateTime dateTime;

    /**
     * 鉴权信息
     */
    private Auth auth;

}
