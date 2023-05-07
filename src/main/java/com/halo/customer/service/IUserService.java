package com.halo.customer.service;

import com.halo.customer.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;
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

    HashMap<String, Object> login(User user);

    String getUsername(String token);

    void logout(String token);

    Boolean register(User user);

    User getPersonal(String username);

    void updatePersonal(User user);

    List<User> getAllUser();

    List<User> getAllRegisteringUser();

    Integer freezeUser(Integer id);

    Integer unfreezeUser(Integer id);

    Integer acceptUser(Integer id);

    Integer rejectUser(Integer id);

    Integer destroyUser(String username);
}
