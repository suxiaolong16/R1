package com.halo.customer.controller;

import com.halo.common.vo.Result;
import com.halo.customer.entity.Comment;
import com.halo.customer.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author halo
 * @since 2023-04-20
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @GetMapping("/getComment")
    public Result<?> getComment(String videoId, String paraId){
        List<Comment> commentList = commentService.getComment(videoId, paraId);
        if(commentList.isEmpty()){
            return Result.fail("未查到评论");
        }
        return Result.success(commentList,"查询评论成功");
    }

    @PostMapping("/addComment")
    public Result<?> addComment(@RequestBody Comment comment){
        Integer num = commentService.addComment(comment);
        if(num == 1){
            return Result.success("添加评论成功！");
        }
        return Result.fail("添加评论失败！");
    }

    @GetMapping("/deleteComment")
    public Result<?> deleteComment(Integer id){
        Integer num = commentService.deleteComment(id);
        if(num == 1){
            return Result.success("删除评论成功！");
        }
        return Result.fail("删除评论失败");
    }
}
