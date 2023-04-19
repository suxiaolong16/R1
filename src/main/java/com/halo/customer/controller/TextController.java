package com.halo.customer.controller;

import com.halo.common.vo.Result;
import com.halo.customer.entity.Text;
import com.halo.customer.service.ITextService;
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
 * @since 2023-04-17
 */
@RestController
@RequestMapping("/text")
@CrossOrigin
public class TextController {

    @Autowired
    private ITextService textService;

    @GetMapping("/getText")
    public Result<?> getText(Integer videoId){
        List<Text> textList = textService.getText(videoId);
        if(textList.isEmpty()){
            return Result.fail("未查到文字记录");
        }
        return Result.success(textList,"查询文字记录成功");
    }
}
