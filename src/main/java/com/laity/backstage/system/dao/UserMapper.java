package com.laity.backstage.system.dao;

import com.laity.backstage.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {
    List<User> getUserList();

    int insert(User record);

    int insertSelective(User record);

    User selectByUsername(User user);
    //禁用用户
    void updateByUserId(User user);
    //启用用户
    void updateByUsername(User user);
}
