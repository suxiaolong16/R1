package com.halo.customer.service;

import com.halo.customer.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author halo
 * @since 2023-04-20
 */
public interface ICommentService extends IService<Comment> {

    List<Comment> getComment(String videoId, String paraId);

    Integer addComment(Comment comment);

    Integer deleteComment(Integer id);
}
