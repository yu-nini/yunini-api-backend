package com.yunini.api.service;

import com.yunini.api.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 校验
     * @param userInterfaceInfo
     * @param add
     */
    void validInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);
    /**
     * 每次调用调用次数加1
     */
    boolean setCount(long userId,long interfaceId);

}
