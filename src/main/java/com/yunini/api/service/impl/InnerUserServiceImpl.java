package com.yunini.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunini.api.service.UserService;
import com.yunini.apicommon.model.entity.User;
import com.yunini.apicommon.service.InnerUserService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * 内部用户实现类
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserService userService;

    @Override
    public User getInvokeUser(String accessKey) {
        //根据ak去查找用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("accessKey",accessKey);
        User user = userService.getOne(queryWrapper);
        return user;
    }
}
