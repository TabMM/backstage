package com.laity.backstage.system.dao;

import com.laity.backstage.system.entity.LaitySysFile;

public interface LaitySysFileMapper {
    int insert(LaitySysFile record);

    int insertSelective(LaitySysFile record);
}