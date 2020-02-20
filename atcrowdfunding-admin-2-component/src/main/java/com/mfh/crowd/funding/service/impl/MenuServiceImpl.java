package com.mfh.crowd.funding.service.impl;

import com.mfh.crowd.funding.entity.Menu;
import com.mfh.crowd.funding.entity.MenuExample;
import com.mfh.crowd.funding.mapper.MenuMapper;
import com.mfh.crowd.funding.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mfh
 * @date 2020/2/19 11:11
 */
@Service
public class MenuServiceImpl implements MenuService {
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return this.menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    @Override

    public Menu getMenuById(Integer menuId) {
        return menuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateByPrimaryKey(menu);
    }

    public MenuMapper getMenuMapper() {
        return menuMapper;
    }

    @Autowired
    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }
}
