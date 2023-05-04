package com.halo.customer.controller;

import com.halo.common.vo.Result;
import com.halo.customer.entity.Summary;
import com.halo.customer.service.IMeetingService;
import com.halo.customer.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author halo
 * @since 2023-04-14
 */
@RestController
@RequestMapping("/meeting")
@CrossOrigin
public class MeetingController {

    @Autowired
    private IMeetingService meetingService;

    @GetMapping("/getSummary")
    public Result<?> getSummary(Integer videoId){
        HashMap<String, String[]> stringHashMap = new HashMap<>();
        stringHashMap = meetingService.getSummary(videoId);
        if(stringHashMap.isEmpty()){
            return Result.fail("未查到信息");
        }else {
            return Result.success(stringHashMap,"查询成功");
        }
    }

    @PostMapping("/addSummary")
    public Result<?> addSummary(@RequestBody Summary summary){
        Integer num = meetingService.addSummary(summary);
        if(num == 1){
            return Result.success("添加成功");
        }
        return Result.fail("添加失败");
    }

    @GetMapping("/deleteSummary")
    public Result<?> deleteSummary(String type, String time, String context, String videoId){
        Integer num = meetingService.deleteSummary(type, time, context, videoId);
        if(num == 1){
            return Result.success("删除成功");
        }
        return Result.fail("删除失败");
    }
}
