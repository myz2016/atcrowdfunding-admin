package com.mfh.crowd.funding.handler;

import com.mfh.crowd.funding.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mfh
 * @date 2020/2/19 11:11
 */
@RestController
public class MenuHandler {
    private MenuService menuService;

    public MenuService getMenuService() {
        return menuService;
    }

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }
}
