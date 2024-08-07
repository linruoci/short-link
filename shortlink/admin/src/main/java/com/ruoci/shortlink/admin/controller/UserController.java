package com.ruoci.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.ruoci.shortlink.admin.common.convention.result.Result;
import com.ruoci.shortlink.admin.common.convention.result.Results;
import com.ruoci.shortlink.admin.dto.req.user.UserLoginReqDTO;
import com.ruoci.shortlink.admin.dto.req.user.UserRegisterReqDTO;
import com.ruoci.shortlink.admin.dto.req.user.UserUpdateReqDTO;
import com.ruoci.shortlink.admin.dto.resp.user.UserActualRespDTO;
import com.ruoci.shortlink.admin.dto.resp.user.UserLoginRespDTO;
import com.ruoci.shortlink.admin.dto.resp.user.UserRespDTO;
import com.ruoci.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制层
 **/
@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    /**
     * 根据用户名查询脱敏用户信息
     */
    @GetMapping("/api/short-link/admin/v1/user/{username}")
    public Result<UserRespDTO> getUserByUsername(@PathVariable("username") String username){
        UserRespDTO userRespDTO = userService.getUserByUsername(username);
        return Results.success(userRespDTO);
    }

    /**
     * 根据用户名查询全部用户信息
     */
    @GetMapping("/api/short-link/admin/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username){
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), UserActualRespDTO.class));
    }

    @GetMapping("/api/short-link/admin/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hasUsername(username));
    }

    /**
     * 注册用户
     */
    @PostMapping("/api/short-link/admin/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO requestParam){
        userService.register(requestParam);
        return Results.success();
    }

    /**
     * 修改用户
     */
    @PutMapping("/api/short-link/admin/v1/user")
    public Result<Void> update(@RequestBody UserUpdateReqDTO requestParam){
        userService.update(requestParam);
        return Results.success();
    }

    /**
     * 用户登录
     */
    @PostMapping("/api/short-link/admin/v1/user/login")
    public Result<UserLoginRespDTO> login(@RequestBody UserLoginReqDTO requestParam){
        return Results.success(userService.login(requestParam));
    }

    /**
     * 检查用户是否登录
     */
    @GetMapping("/api/short-link/admin/v1/user/check-login")
    public Result<Boolean> checkLogin(@RequestParam("username") String username, @RequestParam("token") String token){
        return Results.success(userService.checkLogin(username, token));
    }

    /**
     * 用户退出登录
     */
    @DeleteMapping("/api/short-link/admin/v1/user/logout")
    public Result<Void> logout(@RequestParam("username") String username, @RequestParam("token") String token){
        userService.logout(username, token);
        return Results.success();
    }





}
