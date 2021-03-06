package com.mfh.crowd.funding.service.api;

import com.mfh.crowd.funding.entity.Menu;

import java.util.List;

/**
 * @author mfh
 * @date 2020/2/19 11:11
 */
public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    Menu getMenuById(Integer menuId);

    void updateMenu(Menu menu);
}
