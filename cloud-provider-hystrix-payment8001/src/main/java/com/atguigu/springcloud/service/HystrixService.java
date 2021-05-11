package com.atguigu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.concurrent.TimeUnit;

@Component
public class HystrixService {


    /**
     * 服务熔断
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),//开启熔断机制
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//10次请求
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//超过60%的错误将会触发熔断机制
    })
    public String paymenyCircuitBreaker(@PathVariable("id")Integer id){
        if (id<0){
            throw new RuntimeException("***********id不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水单号:"+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id")Integer id){
        return "id:"+id+"不能为负数，请稍后再试试！";
    }
    /**
     * 性能压力测试
     */
    public String paymentInfo_OK(Integer id){
        return "线程池:  "+Thread.currentThread().getName()+"payment_OK , id:"+id+"\t"+"O(∩_∩)O哈哈~";
    }
    /**
     * 延时测试
     * fallbackMethod = "payInfo_timeOutHander"如果服务超时就会调用这个方法进行超时后的业务处理
     * commandProperties = {
     *             @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
     *             设置对应的属性，如果超过3秒业务没有响应处理
     */
    @HystrixCommand(fallbackMethod = "payInfo_timeOutHander",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String payInfo_timeOut(Integer id){
//        int timeNumber =2;
//        int i =10/0;
        try {
            TimeUnit.SECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池: "+Thread.currentThread().getName()+"paymentInfo_TimeOut, id:"+id+"\t"+"O(∩_∩)O哈哈~";
    }

    public String payInfo_timeOutHander(Integer id){
        return "线程池: "+Thread.currentThread().getName()+"8001系统繁忙或者运行报错，请稍后再试!, id:"+id+"\t";
    }
}
