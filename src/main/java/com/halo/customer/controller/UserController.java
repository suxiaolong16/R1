package com.halo.customer.controller;

import com.halo.common.vo.Result;
import com.halo.customer.entity.User;
import com.halo.customer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author halo
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody User user){
        String data = userService.login(user);
        if(data != null){
            return Result.success(data,"登录成功");
        }
        return Result.fail(20002,"用户名或密码错误");

    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        userService.save(user);
        return Result.success("注册成功");
    }

    @GetMapping("/getUserInfo")
    public Result<String> getUserInfo(String token){
        String username = userService.getUsername(token);
        if(username != null){
            return Result.success(username,"用户名获取成功");
        }
        return Result.fail("用户名获取失败");
    }

}