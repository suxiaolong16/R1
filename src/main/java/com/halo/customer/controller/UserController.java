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
import java.util.HashMap;
import java.util.List;
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
        HashMap<String, Object> map = userService.login(user);
        if((Integer) map.get("flag") == 0){
            String data = (String)map.get("key");
            return Result.success(data,"登录成功");
        }
        if((Integer) map.get("flag") == 1) {
            return Result.fail(20002, "用户名不存在");
        }
        if((Integer) map.get("flag") == 2) {
            return Result.fail(20002, "密码不正确");
        }
        if((Integer) map.get("flag") == 3) {
            return Result.fail(20002, "用户名类型错误");
        }
        if((Integer) map.get("flag") == 4) {
            return Result.fail(20002, "当前用户被封号");
        }
        return Result.fail(20002,"管理员正在审核，请耐心等待");
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));  //对密码加密，并做加盐处理
        Boolean flag = userService.register(user);
        if(flag == true) {
            return Result.success("请等待管理员审核");
        }
        return Result.fail("用户名已存在");
    }

    @GetMapping("/getUserInfo")
    public Result<String> getUserInfo(String token){
        String username = userService.getUsername(token);
        if(username != null){
            System.out.println(username);
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
       userService.updatePersonal(user);
       return Result.success("修改用户信息成功");
    }

    @GetMapping("/getAllUser")
    public Result<?> getAllUser(){
        List<User> userList = userService.getAllUser();
        return Result.success(userList, "获取所有用户成功");
    }

    @GetMapping("/getAllRegisteringUser")
    public Result<?> getAllRegisteringUser(){
        List<User> userList = userService.getAllRegisteringUser();
        return Result.success(userList, "获取正在注册用户成功");
    }

    @GetMapping("/freezeUser")
    public Result<?> freezeUser(Integer id){
        Integer num = userService.freezeUser(id);
        if(num == 1){
            return Result.success("封号成功");
        }
        return Result.fail("封号失败");
    }

    @GetMapping("/unfreezeUser")
    public Result<?> unfreezeUser(Integer id){
        Integer num = userService.unfreezeUser(id);
        if(num == 1){
            return Result.success("解封成功");
        }
        return Result.fail("解封失败");
    }

    @GetMapping("/acceptUser")
    public Result<?> acceptUser(Integer id){
        Integer num = userService.acceptUser(id);
        if(num == 1){
            return Result.success("您已同意该用户注册");
        }
        return Result.fail("操作失败");
    }

    @GetMapping("/rejectUser")
    public Result<?> rejectUser(Integer id){
        Integer num = userService.rejectUser(id);
        if(num == 1){
            return Result.success("您已拒绝该用户注册");
        }
        return Result.fail("操作失败");
    }

    @GetMapping("/destroyUser")
    public Result<?> destroyUser(String username){
        Integer num = userService.destroyUser(username);
        if(num == 1){
            return Result.success("注销成功");
        }
        return Result.fail("注销失败");
    }
}
