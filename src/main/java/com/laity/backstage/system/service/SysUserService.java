package com.laity.backstage.system.service;

import com.laity.backstage.system.entity.User;

import java.util.List;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName SysUserService
 * @Description TODO
 * @createTime 2019/6/4/18:27
 */
public interface SysUserService {
    void login(User user);

    User selectByUsername(User info);

    void register(User user);

    List<User> getUserList();

    //禁用用户
    void updateByUserId(User user);
    //启用用户
    void updateByUsername(User user);
}
