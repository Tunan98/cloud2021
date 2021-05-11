package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigCenter3355Controller {
    @Value("${config.info}")
    private String info;

    @GetMapping("getServerPort")
    public String getServerPort(){
        return info;
    }
}
