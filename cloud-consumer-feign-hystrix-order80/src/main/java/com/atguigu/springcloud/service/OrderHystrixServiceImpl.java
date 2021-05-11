package com.atguigu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class OrderHystrixServiceImpl implements OrderHystrixService{
    @Override
    public String payment_OK(Integer id) {
        return "Order80---------payment_OK";
    }

    @Override
    public String payment_TimeOut(Integer id) {
        return "Order80----------payment_TimeOut";
    }
}
