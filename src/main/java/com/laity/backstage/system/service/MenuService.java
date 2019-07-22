package com.laity.backstage.system.service;

import com.laity.backstage.system.entity.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {
    List<Menu> lodUserMenu(Map<String, Object> map);

    List<Menu> queryAll();
}
