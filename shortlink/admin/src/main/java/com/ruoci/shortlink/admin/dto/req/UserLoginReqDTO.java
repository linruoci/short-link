package com.ruoci.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 一般有三个参数, 用户名, 密码, 验证码
 * 这里我们就直接用户名和密码即可.
 **/
@Data
public class UserLoginReqDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
