package com.yunini.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yunini.api.common.ErrorCode;
import com.yunini.api.exception.BusinessException;
import com.yunini.api.service.UserService;
import com.yunini.apicommon.model.entity.User;
import com.yunini.apicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 内部用户实现类
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserService userService;

    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //根据ak去查找用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accessKey",accessKey);
        //List<User> list = userService.list(queryWrapper);
        User user = userService.getOne(queryWrapper);
        return user;
    }
}
