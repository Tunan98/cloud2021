package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MianSpringBoot7002 {
    public static void main(String[] args) {
        SpringApplication.run(MianSpringBoot7002.class);
    }
}
