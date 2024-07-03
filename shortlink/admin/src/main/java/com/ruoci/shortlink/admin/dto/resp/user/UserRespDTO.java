package com.ruoci.shortlink.admin.dto.resp.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ruoci.shortlink.admin.common.serialize.PhoneDesensitizationSerializer;
import lombok.Data;

/**
 * @Author: ruoci
 * 用户返回参数响应
 **/
@Data
public class UserRespDTO {

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
    @JsonSerialize(using = PhoneDesensitizationSerializer.class)
    private String phone;

    /**
     * 邮箱
     */
    private String email;



}
