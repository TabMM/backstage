package com.laity.backstage.system.service.impl;

import com.laity.backstage.system.dao.UserMapper;
import com.laity.backstage.system.entity.User;
import com.laity.backstage.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName SysUserServiceImpl
 * @Description TODO
 * @createTime 2019/6/4/18:28
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void login(User user) {
        User login = userMapper.selectByUsername(user);
        if (login!=null){
            if (login.getPassword().equals(user.getPassword())){
                System.out.println("用户登录成功");
            }else {
                System.out.println("用户名或密码错误");
            }
        }else {
            System.out.println("用户名不存在");
        }
    }

    @Override
    public User selectByUsername(User info) {
        return userMapper.selectByUsername(info);
    }

    @Override
    public void register(User user) {
        userMapper.insertSelective(user);
    }

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    public void updateByUserId(User user) {
        userMapper.updateByUserId(user);
    }

    @Override
    public void updateByUsername(User user) {
        userMapper.updateByUsername(user);
    }
}
