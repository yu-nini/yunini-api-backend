package com.yunini.api.service.impl;

import com.yunini.api.service.UserInterfaceInfoService;
import com.yunini.apicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * 用户接口的内部实现
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {
    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        //计数 用户每次调用接口 剩余次数减1操作
        return userInterfaceInfoService.invokeCount(userId, interfaceInfoId);
    }
}
