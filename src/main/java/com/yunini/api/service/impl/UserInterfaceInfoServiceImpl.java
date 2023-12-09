package com.yunini.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunini.api.common.ErrorCode;
import com.yunini.api.exception.BusinessException;
import com.yunini.api.mapper.UserInterfaceInfoMapper;
import com.yunini.api.service.UserInterfaceInfoService;
import com.yunini.apicommon.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService{

    @Override
    public void validInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时，所有参数必须非空
        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }
        if (userInterfaceInfo.getLeftNum() < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "剩余次数不能小于 0");
        }
    }

    @Override
    public boolean invokeCount(long userId, long interfaceInfoId) {
        boolean result = true;
        if (userId <= 0 || interfaceInfoId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数错误！");
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        queryWrapper.eq("interfaceInfoId",interfaceInfoId);
        UserInterfaceInfo userInterfaceInfo = this.getOne(queryWrapper);
        if (userInterfaceInfo == null){
            UserInterfaceInfo userInterfaceInfoNew = new UserInterfaceInfo();
            userInterfaceInfoNew.setUserId(userId);
            userInterfaceInfoNew.setInterfaceInfoId(interfaceInfoId);
            userInterfaceInfoNew.setTotalNum(10);
            userInterfaceInfoNew.setLeftNum(9);
            result = this.save(userInterfaceInfoNew);
            if (!result){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户调用接口关系表新建数据错误");
            }
        }else{
            UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.ge("leftNum",0);
            updateWrapper.eq("userId",userId);
            updateWrapper.eq("interfaceInfoId",interfaceInfoId);
            updateWrapper.setSql("leftNum = leftNum - 1, totalNum = totalNum + 1\"");
            result = this.update(updateWrapper);
            if (!result){
                throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户剩余次数为零");
            }
        }
        return true;
    }
}




