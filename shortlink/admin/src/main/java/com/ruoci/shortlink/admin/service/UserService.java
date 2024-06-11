package com.ruoci.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoci.shortlink.admin.dao.entity.UserDO;
import com.ruoci.shortlink.admin.dto.resp.UserRespDTO;

/**
 * @Author: ruoci
 * 用户接口层
 **/
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户信息
     */
    UserRespDTO getUserByUsername(String username);

}
