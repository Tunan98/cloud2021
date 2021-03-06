package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.atguigu.springcloud")
public class ConfigCenter3350 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenter3350.class);
    }
}
