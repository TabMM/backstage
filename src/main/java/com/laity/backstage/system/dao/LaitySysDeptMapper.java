package com.laity.backstage.system.dao;

import com.laity.backstage.system.entity.LaitySysDept;

public interface LaitySysDeptMapper {
    int insert(LaitySysDept record);

    int insertSelective(LaitySysDept record);
}