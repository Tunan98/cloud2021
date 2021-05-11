package com.atguigu.springcloud.controller;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PayMentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String ServerPort;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/payment/get/{id}")
    public CommonResult getById(@PathVariable long id) {

        Payment payment = paymentService.getPaymentById(id);
        return new CommonResult(200, "成功,ServerPort:" + ServerPort, payment);

    }

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment) {

        int i = paymentService.create(payment);
        log.info("*****插入结果：" + i);
        if (i > 0) {
            return new CommonResult(200, "成功,ServerPort:" + ServerPort, i);
        } else {
            return new CommonResult(201, "成功,ServerPort:" + ServerPort, i);
        }
    }
    @GetMapping("/paymnet/client")
    public Object getDiscoveryClient() {
       //获得eureka上的注册服务
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*****"+service);
        }
        //获得eureka中的微服务注册名为CLOUD-PAYMENT-SERVICE的信息，以这个注册名注册的可能有多个所以返回的是一个list集合
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
       //循环遍历输出，获得这个微服务的具体信息，比如端口号，url地址等等
        for (ServiceInstance instanceInfo : serviceInstances) {
            log.info(instanceInfo.getPort()+"\t"+instanceInfo.getHost()+"\t"+instanceInfo.getUri());
        }
        return this.discoveryClient;
    }
    //调用这个方法返回当前服务的端口
    @GetMapping(value = "/payment/lb")
    public String getPaymentLB() {

        return ServerPort;
    }
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
