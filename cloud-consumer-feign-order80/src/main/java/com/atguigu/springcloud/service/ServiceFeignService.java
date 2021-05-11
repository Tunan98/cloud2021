package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")
public interface ServiceFeignService {

    @GetMapping("/payMent/getById/{id}")
    CommonResult<Payment> getById(@PathVariable("id") long id);

    @GetMapping("/payMent/feign/timeout")
    public String paymentFeignTimeout();

}
