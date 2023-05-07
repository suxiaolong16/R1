package com.halo.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author halo
 * @since 2023-03-28
 */
@Data
@TableName("r_video")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String authorName;

    private String name;

    private String mp4path;

    private String imgpath;

    private LocalDateTime time;

    private Integer recycled;

    private LocalDateTime recycledTime;

    private Integer banned;
}
