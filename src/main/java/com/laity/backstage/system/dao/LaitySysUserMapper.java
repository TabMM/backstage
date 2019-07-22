package com.laity.backstage.system.dao;

import com.laity.backstage.system.entity.LaitySysUser;

public interface LaitySysUserMapper {
    int insert(LaitySysUser record);

    int insertSelective(LaitySysUser record);
}