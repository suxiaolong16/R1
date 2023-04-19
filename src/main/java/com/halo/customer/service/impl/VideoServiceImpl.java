package com.halo.customer.service.impl;

import com.halo.customer.entity.Video;
import com.halo.customer.mapper.VideoMapper;
import com.halo.customer.service.IVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
        Integer num = this.baseMapper.deleteById(id);
        return num;
    }
}
