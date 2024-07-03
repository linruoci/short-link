package com.ruoci.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoci.shortlink.admin.dao.entity.UserDO;
import com.ruoci.shortlink.admin.dto.req.user.UserLoginReqDTO;
import com.ruoci.shortlink.admin.dto.req.user.UserRegisterReqDTO;
import com.ruoci.shortlink.admin.dto.req.user.UserUpdateReqDTO;
import com.ruoci.shortlink.admin.dto.resp.user.UserLoginRespDTO;
import com.ruoci.shortlink.admin.dto.resp.user.UserRespDTO;

/**
 * @Author: ruoci
 * 用户接口层
 **/
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户信息
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 查询用户名是否可用
     * @param username 用户名
     * @return 用户名存在返回False, 不存在返回True
     */
    Boolean hasUsername(String username);

    /**
     * 用户注册功能
     * @param requestParam 接收参数
     * @return 无返回值
     */
    void register(UserRegisterReqDTO requestParam);


    /**
     * 根据用户名修改用户
     * @param requestParam 修改用户请求参数
     */
    void update(UserUpdateReqDTO requestParam);


    /**
     * 用户登录
     * @param requestParam 用户登录请求参数
     * @return 用户标识 token
     */
    UserLoginRespDTO login(UserLoginReqDTO requestParam);

    /**
     * 用户是否登录
     * @return 用户是否登录
     */
    Boolean checkLogin(String username, String token);


    /**
     * 用户退出登录接口
     */
    void logout(String username, String token);






}
