package com.laity.backstage.system.dao;

import com.laity.backstage.system.entity.Menu;

public interface MenuMapper {
    int insert(Menu record);

    int insertSelective(Menu record);
}