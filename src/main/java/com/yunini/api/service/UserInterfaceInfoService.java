package com.yunini.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunini.apicommon.model.entity.UserInterfaceInfo;

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
    boolean invokeCount(long userId,long interfaceId);

}
