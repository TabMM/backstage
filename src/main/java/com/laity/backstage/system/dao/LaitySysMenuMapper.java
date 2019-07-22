package com.laity.backstage.system.dao;

import com.laity.backstage.system.entity.LaitySysMenu;

public interface LaitySysMenuMapper {
    int insert(LaitySysMenu record);

    int insertSelective(LaitySysMenu record);
}