package com.liang.netty.demo.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;

@Data
public class MsgInfo {

   private String uuid;

   private String sessionId;

   private String fromUserId;

   private String toUserId;

   private Integer msgType;

   private String msgData;

   private DateTime dateTime;
}
