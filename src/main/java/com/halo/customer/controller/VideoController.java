package com.halo.customer.controller;

import com.halo.common.vo.Result;
import com.halo.customer.entity.Video;
import com.halo.customer.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import com.halo.util.ImageUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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

    //一定要注意这个函数中文件的路径，改动路径可能会上传失败
    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file, String title, String author) throws IOException {
        String filename = file.getOriginalFilename();
        String mp4Path = "/video/mp4/" + filename;
        String path = "D:\\VueProject\\R2\\public\\video\\mp4";
        String imgFileName = filename.split("\\.")[0] + ".jpg";
        String imgPath = "/video/img/" + imgFileName;
        String absoluteMp4Path = "D:\\VueProject\\R2\\public\\video\\mp4\\" + filename;
        String absoluteImgPath = "D:\\VueProject\\R2\\public\\video\\img\\" + imgFileName;
        try {
            File file1 = new File(path + "/" + filename);
            file.transferTo(file1);
            ImageUtil.videoImage(absoluteMp4Path,absoluteImgPath);
            videoService.uploadVideo(author, title, mp4Path, imgPath);
            return Result.success("上传成功");
        }catch (IOException e){
            return Result.fail("上传失败");
        }
    }

    @GetMapping("/getAllVideoByAdmin")
    public Result<?> getAllVideoByAdmin(){
        List<Video> videoList = videoService.getAllVideoByAdmin();
        if(videoList.isEmpty()){
            return Result.fail("未查到视频信息");
        }else{
            return Result.success(videoList,"查询成功");
        }
    }

    @GetMapping("/banVideo")
    public Result<?> banVideo(Integer id){
        Integer num = videoService.banVideo(id);
        if(num == 1){
            return Result.success("暂时封禁成功");
        }
        return Result.fail("暂时封禁失败");
    }

    @GetMapping("/liftVideo")
    public Result<?> liftVideo(Integer id){
        Integer num = videoService.liftVideo(id);
        if(num == 1){
            return Result.success("解禁成功");
        }
        return Result.fail("解禁失败");
    }
}
