package com.ruoci.shortlink.admin.dto.resp.user;

import lombok.Builder;
import lombok.Data;

/**
 * 用户登录接口返回响应
 **/
@Data
@Builder
public class UserLoginRespDTO {

    /**
     * 用户token
     */
    private String token;

}
