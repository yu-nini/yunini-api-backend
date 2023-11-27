package com.yunini.api.service;

import com.yunini.api.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

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
