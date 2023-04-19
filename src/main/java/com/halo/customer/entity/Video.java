package com.halo.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author halo
 * @since 2023-03-28
 */
@TableName("r_video")
public class Video implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer author_id;

    private String author_name;

    private String name;

    private String mp4path;

    private String imgpath;

    private String context;

    private LocalDateTime time;

    private Integer recycled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMp4path() {
        return mp4path;
    }

    public void setMp4path(String mp4path) {
        this.mp4path = mp4path;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Integer getRecycled() {
        return recycled;
    }

    public void setRecycled(Integer recycled) {
        this.recycled = recycled;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", author_id=" + author_id +
                ", author_name='" + author_name + '\'' +
                ", name='" + name + '\'' +
                ", mp4path='" + mp4path + '\'' +
                ", imgpath='" + imgpath + '\'' +
                ", context='" + context + '\'' +
                ", time=" + time +
                ", recycled=" + recycled +
                '}';
    }

    public Video(Integer id, Integer author_id, String author_name, String name, String mp4path, String imgpath, String context, LocalDateTime time, Integer recycled) {
        this.id = id;
        this.author_id = author_id;
        this.author_name = author_name;
        this.name = name;
        this.mp4path = mp4path;
        this.imgpath = imgpath;
        this.context = context;
        this.time = time;
        this.recycled = recycled;
    }
}
