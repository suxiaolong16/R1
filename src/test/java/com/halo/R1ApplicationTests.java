package com.halo;

import com.halo.customer.entity.User;
import com.halo.customer.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class R1ApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testMapper() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void testRedis(){
        Object loginUser = redisTemplate.opsForValue().get("user:ccd8aaff-aadf-40d5-a6c6-a941d333fafb");
        User user = ((User) loginUser);
        System.out.println(user.getUsername());
    }

}
