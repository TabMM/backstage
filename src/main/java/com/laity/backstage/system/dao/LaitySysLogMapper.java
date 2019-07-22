package com.laity.backstage.system.dao;

import com.laity.backstage.system.entity.LaitySysLog;

public interface LaitySysLogMapper {
    int insert(LaitySysLog record);

    int insertSelective(LaitySysLog record);
}