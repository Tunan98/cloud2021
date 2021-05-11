package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.ServiceFeignService;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FeignController {
    @Autowired
     private ServiceFeignService serviceFeignService;
    @GetMapping("consumer/payment/get/{id}")
    public CommonResult<Payment>getPaymentById(@PathVariable("id")Long id){
        return serviceFeignService.getById(id);
    }


    @GetMapping("consumer/payment/timetout")
    public String getTime(){

        return serviceFeignService.paymentFeignTimeout();
    }
}
