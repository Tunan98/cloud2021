package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PayMentController {
    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    private String ServerPort;
    @GetMapping("/payment/get/{id}")
    public CommonResult getById(@PathVariable long id){

        Payment payment = paymentService.getPaymentById(id);
        return new CommonResult(200,"成功,ServerPort:"+ServerPort,payment);

    }

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {

        int i = paymentService.create(payment);
        log.info("*****插入结果："+i);
        if (i > 0) {
            return new CommonResult(200, "成功,ServerPort:"+ServerPort, i);
        } else {
            return new CommonResult(201, "成功,ServerPort:"+ServerPort, i);
        }
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {
        return ServerPort;
    }
        //延时演示
    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ServerPort;
    }
}
