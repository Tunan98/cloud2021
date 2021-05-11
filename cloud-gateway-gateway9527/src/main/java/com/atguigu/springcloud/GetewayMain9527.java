package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.time.ZonedDateTime;

@SpringBootApplication
@EnableEurekaClient
public class GetewayMain9527 {
    public static void main(String[] args) {
        SpringApplication.run(GetewayMain9527.class,args);
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
    }
}
