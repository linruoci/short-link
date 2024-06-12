package com.ruoci.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoci.shortlink.admin.common.convention.exception.ClientException;
import com.ruoci.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.ruoci.shortlink.admin.dao.entity.UserDO;
import com.ruoci.shortlink.admin.dao.mapper.UserMapper;
import com.ruoci.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.ruoci.shortlink.admin.dto.resp.UserRespDTO;
import com.ruoci.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: ruoci
 * 用户接口实现层
 **/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {


    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;


    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);

        UserDO userDO = this.getOne(queryWrapper);
        UserRespDTO result = new UserRespDTO();

        if (userDO == null){
            throw new ClientException(UserErrorCodeEnum.USER_NULL);
        }

        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    @Override
    public Boolean hasUsername(String username) {
        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if (!hasUsername(requestParam.getUsername())){
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        }
        int row = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
        if (row < 1){
            throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROR);
        }
        userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
    }


}
