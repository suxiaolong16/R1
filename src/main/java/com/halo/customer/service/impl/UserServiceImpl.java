package com.halo.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.halo.customer.entity.Meeting;
import com.halo.customer.entity.User;
import com.halo.customer.mapper.UserMapper;
import com.halo.customer.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author halo
 * @since 2023-03-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(User user) {
        //根据用户名和密码查询
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("username",user.getUsername());
        List<User> users = this.baseMapper.selectByMap(queryMap);
        User loginUser = users.get(0);
        //结果不为空，则生成token，并将用户信息存入redis
        if(loginUser != null && passwordEncoder.matches(user.getPassword(),loginUser.getPassword()) && user.getType() == loginUser.getType()){
            String key = "token:" + UUID.randomUUID();

            //存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,5, TimeUnit.HOURS);

            //返回数据
            return key;
        }
        return null;
    }

    @Override
    public String getUsername(String token) {
        Object loginUser = redisTemplate.opsForValue().get(token);
        User user = ((User) loginUser);
        String username = user.getUsername();
        return username;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

    @Override
    public Boolean register(User user) {
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("username",user.getUsername());
        List<User> users = this.baseMapper.selectByMap(queryMap);
        if(users.isEmpty()){
            save(user);
            return true;
        }
        return false;
    }

    @Override
    public User getPersonal(String username) {
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("username",username);
        List<User> users = this.baseMapper.selectByMap(queryMap);
        User user = users.get(0);
        user.setPassword(null);
        return user;
    }

    @Override
    public void updatePersonal(User user) {
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("username",user.getUsername());
        List<User> users = this.baseMapper.selectByMap(queryMap);
        User resultUser = users.get(0);
        resultUser.setGender(user.getGender());
        resultUser.setEmail(user.getEmail());
        resultUser.setAddress(user.getAddress());
        resultUser.setSignature(user.getSignature());
        this.baseMapper.updateById(resultUser);
    }
}
