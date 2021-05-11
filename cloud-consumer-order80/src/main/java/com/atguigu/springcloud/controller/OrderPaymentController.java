package com.atguigu.springcloud.controller;
import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.lb.ChooseLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("order")
@Slf4j
public class OrderPaymentController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ChooseLoadBalancer chooseLoadBalancer;
    @Autowired
    private DiscoveryClient discoveryClient;


   // private static  final String PAYMENT_CLIENT = "http://localhost:8001";
    private static  final String PAYMENT_CLIENT = "http://CLOUD-PAYMENT-SERVICE";

    @GetMapping("consumer/playment/cteate")
    public CommonResult<Payment> create(@RequestBody Payment payment){
       return restTemplate.postForObject(PAYMENT_CLIENT+"/payMent/payment/create/", payment,CommonResult.class);
    }


    @GetMapping("consumer/payment/get/{id}")
    public CommonResult getId(@PathVariable("id")long id){
       return restTemplate.getForObject(PAYMENT_CLIENT+"/payMent/getById/"+id,CommonResult.class);
    }

    @GetMapping("/consumer/payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size()<=0){
            return  null;
        }
        ServiceInstance serviceInstance = chooseLoadBalancer.instances(instances);
        URI uri = serviceInstance.getUri();
        return  restTemplate.getForObject(uri+"/payMent/payment/lb",String.class);
    }

}
