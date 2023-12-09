package com.yunini.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunini.api.service.InterfaceInfoService;
import com.yunini.apicommon.model.entity.InterfaceInfo;
import com.yunini.apicommon.service.InnerInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoService interfaceInfoService;
    //根据接口路径和接口方法查找接口
    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url",url);
        queryWrapper.eq("method",method);
        InterfaceInfo interfaceInfo = interfaceInfoService.getOne(queryWrapper);
        return interfaceInfo;
    }
}
