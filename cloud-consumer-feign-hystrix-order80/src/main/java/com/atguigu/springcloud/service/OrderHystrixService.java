package com.atguigu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-PAYMENT",fallback = OrderHystrixServiceImpl.class)//超时处理的业务类
public interface OrderHystrixService {

    /**
     * order消费端测试hystrix8001性能
     * @param id
     * @return
     */

    /**
     * 正常访问
     */
    @GetMapping("/payment/hystrix/ok/{id}")
    public String payment_OK(@PathVariable("id")Integer id);

    /**
     *超时时间
     * @param id
     * @return
     */
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String payment_TimeOut(@PathVariable("id")Integer id);
}
