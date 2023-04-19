package com.halo.customer.service.impl;

import com.halo.customer.entity.Text;
import com.halo.customer.entity.Video;
import com.halo.customer.mapper.TextMapper;
import com.halo.customer.service.ITextService;
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
 * @since 2023-04-17
 */
@Service
public class TextServiceImpl extends ServiceImpl<TextMapper, Text> implements ITextService {

    @Override
    public List<Text> getText(Integer videoId) {
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("video_id",videoId);
        List<Text> textList = this.baseMapper.selectByMap(map);
        return textList;
    }
}
