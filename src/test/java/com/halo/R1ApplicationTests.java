package com.halo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.halo.customer.entity.User;
import com.halo.customer.entity.Video;
import com.halo.customer.mapper.UserMapper;
import com.halo.customer.mapper.VideoMapper;
import com.halo.customer.service.impl.VideoServiceImpl;
import org.bytedeco.javacv.FrameGrabber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import com.halo.util.ImageUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
class R1ApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Resource
    private VideoMapper videoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private VideoServiceImpl videoService;

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

    @Test
    void testVideo(){
        List<Video> videoList = this.videoMapper.selectList(null);
        videoList.forEach(System.out::println);
    }

    @Test
    void  testSplit(){
        String aaa = "/video/img/10.jpg";
        String[] split = aaa.split("/");
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);
        }
        System.out.println(split.length);
    }

    @Test
    void testPng() throws FrameGrabber.Exception {
        ImageUtil.videoImage("D:\\VueProject\\online-video-website-master\\public\\video\\mp4\\10.mp4","C:\\Users\\86155\\Desktop\\10.jpg");
    }

    @Test
    void testa() {
        videoService.recover(10);
    }

    @Test
    void testDay(){
        LocalDateTime expiredTime = LocalDateTime.now().minusDays(30);
        System.out.println(expiredTime);
    }

}
