package com.halo.customer.service.impl;

import com.halo.customer.entity.Comment;
import com.halo.customer.mapper.CommentMapper;
import com.halo.customer.service.ICommentService;
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
 * @since 2023-04-20
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Override
    public List<Comment> getComment(String videoId, String paraId) {
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("video_id",videoId);
        queryMap.put("para_id",paraId);
        List<Comment> commentList = this.baseMapper.selectByMap(queryMap);
        return commentList;
    }

    @Override
    public Integer addComment(Comment comment) {
        Integer num = this.baseMapper.insert(comment);
        return num;
    }

    @Override
    public Integer deleteComment(Integer id) {
        Integer num = this.baseMapper.deleteById(id);
        return num;
    }
}
