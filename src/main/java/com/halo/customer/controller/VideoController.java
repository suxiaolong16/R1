package com.halo.customer.controller;

import com.halo.common.vo.Result;
import com.halo.customer.entity.Video;
import com.halo.customer.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author halo
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private IVideoService videoService;

    @GetMapping("/getAllVideo")
    public Result<?> getAllVideo(){
        List<Video> videoList = videoService.getAllVideo();
        if(videoList.isEmpty()){
            return Result.fail("未查到视频信息");
        }else{
            return Result.success(videoList,"查询成功");
        }
    }

    @GetMapping("/getMyVideo")
    public Result<?> getMyVideo(String username){
        List<Video> videoList = videoService.getMyVideo(username);
        if(videoList.isEmpty()){
            return Result.fail("未查到视频信息");
        }else{
            return Result.success(videoList,"查询成功");
        }
    }

    @GetMapping("/getRecycledVideo")
    public Result<?> getRecycledVideo(String username){
        List<Video> videoList = videoService.getRecycledVideo(username);
        if(videoList.isEmpty()){
            return Result.fail("未查到视频信息");
        }else{
            return Result.success(videoList,"查询成功");
        }
    }

    @GetMapping("/getVideoById")
    public Result<?> getVideoById(Integer id){
        Video resultVideo = videoService.getVideoById(id);
        if(resultVideo == null){
            return Result.fail("未查到视频信息");
        }else{
            return Result.success(resultVideo,"查询成功");
        }
    }

    @GetMapping("/recycle")
    public  Result<?> recycle(Integer id){
        Integer num = videoService.recycle(id);
        if(num == 1){
            return Result.success("回收成功");
        }
        return Result.fail("回收失败");
    }

    @GetMapping("/recover")
    public  Result<?> recover(Integer id){
        Integer num = videoService.recover(id);
        if(num == 1){
            return Result.success("恢复成功");
        }
        return Result.fail("恢复失败");
    }

    @GetMapping("/deleteForever")
    public  Result<?> deleteForever(Integer id){
        Integer num = videoService.deleteForever(id);
        if(num == 1){
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }
}
