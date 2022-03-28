package com.liang.netty.demo.entity;

import lombok.Data;

@Data
public class Auth {

    private String appName;

    private String accessKey;

    private String secretKey;
}
