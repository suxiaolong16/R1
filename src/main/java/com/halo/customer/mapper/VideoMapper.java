package com.halo.customer.mapper;

import com.halo.customer.entity.Video;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author halo
 * @since 2023-03-28
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {

    @Select("SELECT * FROM r_video WHERE recycled_time <= #{expiredTime} AND recycled = 1")
    List<Video> findExpiredVideos(@Param("expiredTime") LocalDateTime expiredTime);
}
