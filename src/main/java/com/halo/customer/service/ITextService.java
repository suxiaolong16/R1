package com.halo.customer.service;

import com.halo.customer.entity.Text;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author halo
 * @since 2023-04-17
 */
public interface ITextService extends IService<Text> {

    List<Text> getText(Integer videoId);
}
