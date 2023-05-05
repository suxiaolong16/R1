package com.halo.customer.controller;

import com.halo.common.vo.Result;
import com.halo.customer.entity.User;
import com.halo.customer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Result<String> login(@RequestBody User user){
        String data = userService.login(user);
        if(data != null){
            return Result.success(data,"登录成功");
        }
        return Result.fail(20002,"登录失败");

    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));  //对密码加密，并做加盐处理
        Boolean flag = userService.register(user);
        if(flag == true) {
            return Result.success("注册成功");
        }
        return Result.fail("注册失败");
    }

    @GetMapping("/getUserInfo")
    public Result<String> getUserInfo(String token){
        String username = userService.getUsername(token);
        if(username != null){
            return Result.success(username,"用户名获取成功");
        }
        return Result.fail("用户名获取失败");
    }

    @GetMapping("/logout")
    public Result<?> logout(String token){
        userService.logout(token);
        return Result.success("退出登录成功");
    }

    @GetMapping("/getPersonal")
    public Result<?> getPersonal(String username){
        User user = userService.getPersonal(username);
        if(user == null){
            return Result.fail("个人信息获取失败");
        }
        return Result.success(user,"个人信息获取成功");
    }

    @PostMapping("/updatePersonal")
    public Result<?> updatePersonal(@RequestBody User user){
        System.out.println(user.getUsername());
       userService.updatePersonal(user);
       return Result.success("修改用户信息成功");
    }
}
