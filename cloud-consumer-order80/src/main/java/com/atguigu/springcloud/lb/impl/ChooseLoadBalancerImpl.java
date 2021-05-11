package com.atguigu.springcloud.lb.impl;

import com.atguigu.springcloud.lb.ChooseLoadBalancer;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ChooseLoadBalancerImpl implements ChooseLoadBalancer {
    //初始值，用于下面获取服务索引计算
    private AtomicInteger atomicInteger =new AtomicInteger(0);
    //获得下一个要调用的服务的索引地址，用于具体调用对应的服务地址
    public final int getAndIncrement(){
        int current;
        int next;
        do{
          current = this.atomicInteger.get();
            //这里进行判断，如果current大于interger最大值则赋值为0,否则+1进行下一步的取余运算
           next= current>=2147483647 ? 0 : current + 1;
        }while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println("********第"+next+"次访问********");
        return next;
    }
          //获得注册中心集群里面的注册实例总数
    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
          //进行取余运算，获得对应服务下标
      int index = getAndIncrement()%serviceInstances.size();
      //将对应下标里面的微服务返回
        return serviceInstances.get(index);
    }
}
