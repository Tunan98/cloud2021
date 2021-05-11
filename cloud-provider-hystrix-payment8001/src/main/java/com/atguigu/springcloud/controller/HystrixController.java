package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.HystrixService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class HystrixController {
    @Value("${server.port}")
    private String ServerPort;
    @Autowired
    private HystrixService hystrixService;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String payment_OK(@PathVariable("id")Integer id){
       String result =  hystrixService.paymentInfo_OK(id);
        log.info("****result:"+result);
       return  result;
    }

    /**
     * 服务延时，测试降级
     * @param id
     * @return
     */
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String payment_TimeOut(@PathVariable("id")Integer id){
        String result =  hystrixService.payInfo_timeOut(id);
        log.info("****result:"+result);
        return  result;
    }
    /**
     * 演示服务熔断
     */
    @GetMapping("/payment/circult/{id}")
    public String paymentRonDuan(@PathVariable("id")Integer id){
       String result = hystrixService.paymenyCircuitBreaker(id);
       log.info("*******result:"+result);
       return result;
    }
}
