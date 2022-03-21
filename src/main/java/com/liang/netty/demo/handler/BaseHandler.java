package com.liang.netty.demo.handler;

import com.liang.netty.demo.service.MsgService;
import com.liang.netty.demo.service.impl.MsgServiceImpl;
import com.liang.netty.demo.util.SpringUtil;

public interface BaseHandler {

    default MsgService getMsgService() {
        return SpringUtil.getBean(MsgServiceImpl.class);
    };


}
