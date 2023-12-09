package com.yunini.api.demo.impl;

import com.yunini.api.demo.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("服务提供者");
        return "提供者"+name;
    }
}
