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

import java.util.*;
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
    public HashMap<String, Object> login(User user) {
        //根据用户名和密码查询
        HashMap<String, Object> resultMap = new HashMap<>();
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("username", user.getUsername());
        List<User> users = this.baseMapper.selectByMap(queryMap);
        if (users.isEmpty()) {
            resultMap.put("flag", 1);
            return resultMap;
        }
        User loginUser = users.get(0);
        //结果不为空，则生成token，并将用户信息存入redis
        if (passwordEncoder.matches(user.getPassword(), loginUser.getPassword()) && user.getType() == loginUser.getType()
                && loginUser.getState() == 0 && loginUser.getAccepted() == 1) {
            String key = "token:" + UUID.randomUUID();

            //存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key, loginUser, 5, TimeUnit.HOURS);

            resultMap.put("key", key);
            resultMap.put("flag", 0);
            //返回数据
            return resultMap;
        }
        if (loginUser.getAccepted() == 0) {
            resultMap.put("flag", 5);
            return resultMap;
        }
        if (loginUser.getState() == 1){
            resultMap.put("flag", 4);
            return resultMap;
        }
        if (!passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
            resultMap.put("flag", 2);
            return resultMap;
        }
            resultMap.put("flag", 3);
            return resultMap;
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

    @Override
    public List<User> getAllUser() {
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("type",1);
        queryMap.put("accepted",1);
        List<User> userList = this.baseMapper.selectByMap(queryMap);
        return userList;
    }

    @Override
    public List<User> getAllRegisteringUser() {
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("accepted",0);
        List<User> userList = this.baseMapper.selectByMap(queryMap);
        return userList;
    }

    @Override
    public Integer freezeUser(Integer id) {
        User user = this.baseMapper.selectById(id);
        user.setState(1);
        Integer num = this.baseMapper.updateById(user);
        return num;
    }

    @Override
    public Integer unfreezeUser(Integer id) {
        User user = this.baseMapper.selectById(id);
        user.setState(0);
        Integer num = this.baseMapper.updateById(user);
        return num;
    }

    @Override
    public Integer acceptUser(Integer id) {
        User user = this.baseMapper.selectById(id);
        user.setAccepted(1);
        Integer num = this.baseMapper.updateById(user);
        return num;
    }

    @Override
    public Integer rejectUser(Integer id) {
        Integer num = this.baseMapper.deleteById(id);
        return num;
    }

    @Override
    public Integer destroyUser(String username) {
        HashMap<String,Object> queryMap = new HashMap<>();
        queryMap.put("username",username);
        List<User> userList = this.baseMapper.selectByMap(queryMap);
        User user = userList.get(0);
        Integer num = this.baseMapper.deleteById(user);
        return num;
    }
}
