package com.yunini.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunini.apicommon.model.entity.UserInterfaceInfo;

import java.util.List;

/**
 * @Entity com.yunini.api.model.entity.UserInterfaceInfo
 */
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> listTopInvokeInterfaceInfo(int limit);
}




