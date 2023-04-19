package com.halo.customer.service;

import com.halo.customer.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author halo
 * @since 2023-03-28
 */
public interface IVideoService extends IService<Video> {

    List<Video> getAllVideo();

    List<Video> getMyVideo(String username);

    List<Video> getRecycledVideo(String username);

    Video getVideoById(Integer id);

    Integer recycle(Integer id);

    Integer recover(Integer id);

    Integer deleteForever(Integer id);
}
