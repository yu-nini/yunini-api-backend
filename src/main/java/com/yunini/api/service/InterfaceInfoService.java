package com.yunini.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunini.apicommon.model.entity.InterfaceInfo;

/**
 *
 */
public interface InterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 校验
     * @param post
     * @param add
     */
    void validInterfaceInfo(InterfaceInfo post, boolean add);

}
