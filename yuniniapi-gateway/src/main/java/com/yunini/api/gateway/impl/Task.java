package com.yunini.api.gateway.impl;

import com.yunini.apicommon.service.InnerUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component

public class Task implements CommandLineRunner {
    @DubboReference
    private InnerUserService innerUserService;

    @Override
    public void run(String... args) throws Exception {
       /* User user = innerUserService.getInvokeUser("656c6524f92d6630c8197224bd23f161");
        System.out.println("user"+user.getId());*/
    }
}
