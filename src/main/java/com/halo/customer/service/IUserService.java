package com.halo.customer.service;

import com.halo.customer.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author halo
 * @since 2023-03-28
 */
public interface IUserService extends IService<User> {

    String login(User user);

    String getUsername(String token);
}
