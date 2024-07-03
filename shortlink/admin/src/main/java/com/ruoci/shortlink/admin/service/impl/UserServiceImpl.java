package com.ruoci.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoci.shortlink.admin.common.constant.RedisCacheConstant;
import com.ruoci.shortlink.admin.common.convention.exception.ClientException;
import com.ruoci.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.ruoci.shortlink.admin.dao.entity.UserDO;
import com.ruoci.shortlink.admin.dao.mapper.UserMapper;
import com.ruoci.shortlink.admin.dto.req.UserLoginReqDTO;
import com.ruoci.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.ruoci.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.ruoci.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.ruoci.shortlink.admin.dto.resp.UserRespDTO;
import com.ruoci.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: ruoci
 * 用户接口实现层
 **/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {


    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    private final RedissonClient redissonClient;

    private final StringRedisTemplate stringRedisTemplate;


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
        RLock lock = redissonClient.getLock(RedisCacheConstant.LOCK_USER_REGISTER_KEY + requestParam.getUsername());
        try{
            if (lock.tryLock()){
                int row = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
                if (row < 1){
                    throw new ClientException(UserErrorCodeEnum.USER_SAVE_ERROR);
                }
                userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
            }
        } finally {
            lock.unlock();
        }

    }

    @Override
    public void update(UserUpdateReqDTO requestParam) {
//        TODO 验证当前用户是否为登录用户, 如果不是不允许修改!
        LambdaUpdateWrapper<UserDO> wrapper = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam, UserDO.class), wrapper);
    }


    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername())
                .eq(UserDO::getPassword, requestParam.getPassword());
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null){
            throw new ClientException("用户不存在!");
        }

        Boolean hasLogin = stringRedisTemplate.hasKey(RedisCacheConstant.LOGIN_USER_KEY + requestParam.getUsername());
        if (hasLogin != null && hasLogin){
            throw new ClientException("用户已登录");
        }

        /**
         * key:login_用户名
         * value:
         *  key: 生成的token标识
         *  value: JSON字符串(用户信息)
         */
        String token = UUID.randomUUID().toString(true);

        stringRedisTemplate.opsForHash().put(RedisCacheConstant.LOGIN_USER_KEY + requestParam.getUsername(),
                token, JSONUtil.toJsonStr(userDO));
        stringRedisTemplate.expire(RedisCacheConstant.LOGIN_USER_KEY + requestParam.getUsername(), 30L, TimeUnit.MINUTES);
        return UserLoginRespDTO.builder()
                .token(token)
                .build();

    }

    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get(RedisCacheConstant.LOGIN_USER_KEY + username, token) != null;
    }

    @Override
    public void logout(String username, String token) {
        if (checkLogin(username, token)){
            stringRedisTemplate.delete(RedisCacheConstant.LOGIN_USER_KEY + username);
            return;
        }
        throw new ClientException("用户Token不存在或用户未登录");
    }


}
