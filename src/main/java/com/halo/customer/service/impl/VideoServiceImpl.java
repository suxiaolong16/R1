package com.halo.customer.service.impl;

import com.halo.customer.entity.Video;
import com.halo.customer.mapper.VideoMapper;
import com.halo.customer.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author halo
 * @since 2023-03-28
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

    @Autowired
    private VideoMapper videoMapper;

    @Override
    public List<Video> getAllVideo() {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("recycled",0);
        List<Video> videoList = this.baseMapper.selectByMap(map);
        return videoList;
    }

    @Override
    public List<Video> getMyVideo(String username) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("recycled",0);
        map.put("author_name",username);
        List<Video> videoList = this.baseMapper.selectByMap(map);
        return videoList;
    }

    @Override
    public List<Video> getRecycledVideo(String username) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("recycled",1);
        map.put("author_name",username);
        List<Video> videoList = this.baseMapper.selectByMap(map);
        return videoList;
    }

    @Override
    public Video getVideoById(Integer id) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        List<Video> videoList = this.baseMapper.selectByMap(map);
        Video video = videoList.get(0);
        return video;
    }

    @Override
    public Integer recycle(Integer id) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        List<Video> videoList = this.baseMapper.selectByMap(map);
        if(videoList.isEmpty()){
            return 0;
        }
        Video video = videoList.get(0);
        video.setRecycled(1);
        LocalDateTime currentDateTime = LocalDateTime.now();
        video.setRecycledTime(currentDateTime);
        this.baseMapper.updateById(video);
        return 1;
    }

    @Override
    public Integer recover(Integer id) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        List<Video> videoList = this.baseMapper.selectByMap(map);
        if(videoList.isEmpty()){
            return 0;
        }
        Video video = videoList.get(0);
        video.setRecycled(0);
        this.baseMapper.updateById(video);
        return 1;
    }

    @Override
    public Integer deleteForever(Integer id) {
        Video video = this.baseMapper.selectById(id);
        String mp4Path = video.getMp4path();
        String imgPath = video.getImgpath();

        String mp4FileName = mp4Path.split("/")[3];
        String imgFileName = imgPath.split("/")[3];

        String absoluteMp4Path = "D:\\VueProject\\online-video-website-master\\public\\video\\mp4\\" + mp4FileName;
        String absoluteImgPath = "D:\\VueProject\\online-video-website-master\\public\\video\\img\\" + imgFileName;

        File mp4File = new File(absoluteMp4Path);
        if (mp4File.isFile() && mp4File.exists()) {
            mp4File.delete();
        }
        File imgFile = new File(absoluteImgPath);
        if (imgFile.isFile() && imgFile.exists()) {
            imgFile.delete();
        }

        Integer num = this.baseMapper.deleteById(id);
        return num;
    }

    @Override
    public void uploadVideo(String author, String title, String mp4Path, String imgPath) {
        Video video = new Video();
        video.setAuthorName(author);
        video.setName(title);
        video.setMp4path(mp4Path);
        video.setImgpath(imgPath);
        video.setRecycled(0);
        this.baseMapper.insert(video);
    }

    @Scheduled(cron = "0 0 20 * * ?") // 每天晚上8点执行
    public void cleanTrash(){
        LocalDateTime expiredTime = LocalDateTime.now().minusDays(30);
        List<Video> expiredVideos = videoMapper.findExpiredVideos(expiredTime);
        for (Video video : expiredVideos) {
            // 删除回收站中超过30天的视频
            deleteForever(video.getId());
        }
    }
}
