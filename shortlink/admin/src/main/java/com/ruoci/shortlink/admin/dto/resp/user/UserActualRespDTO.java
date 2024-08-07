package com.ruoci.shortlink.admin.dto.resp.user;

import lombok.Data;

/**
 * @Author: ruoci
 * 用户返回参数响应
 **/
@Data
public class UserActualRespDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;
    
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
