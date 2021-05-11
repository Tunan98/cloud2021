package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.OrderHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")
public class OrderHystrixController {

    /**
     * 调用paymenthystrix8001进行性能压力测试
     */
    @Autowired
    private OrderHystrixService orderHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String payment_OK(@PathVariable("id")Integer id){
        return orderHystrixService.payment_OK(id);
    }

    /**
     * 客户端服务降级处理
     * @param id
     * @return
     */

    @GetMapping("/consumer/payment/hystrix/timeOut/{id}")
    @HystrixCommand(fallbackMethod = "TimeOutHander",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })
    public String payment_TimeOut(@PathVariable("id")Integer id){
        int i =10/0;
        return orderHystrixService.payment_TimeOut(id);
    }

    public String TimeOutHander(@PathVariable("id")Integer id){
        return "消费者80你好:对方支付系统Hystrix8001服务器繁忙！请稍后再试!";
    }

    /**
     * 全局fallbaack处理
     * @param id
     * @return
     */

    public String payment_Global_FallbackMethod(@PathVariable("id")Integer id){
        return "Global异常处理信息,请稍后再试。";
    }

}
