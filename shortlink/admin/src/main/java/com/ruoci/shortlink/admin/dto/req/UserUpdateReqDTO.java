package com.ruoci.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @Author: ruoci
 * 用户注册请求实体类
 **/
@Data
public class UserUpdateReqDTO {


    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;


}
