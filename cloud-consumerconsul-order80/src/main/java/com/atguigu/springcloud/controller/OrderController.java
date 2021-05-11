package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {



    private static final String SERVER_PORT ="http://consul-provider-payment";
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/consul")
    public String paymentInfo(){
      return   restTemplate.getForObject(SERVER_PORT+"/payment/consul",String.class);
    }
}
