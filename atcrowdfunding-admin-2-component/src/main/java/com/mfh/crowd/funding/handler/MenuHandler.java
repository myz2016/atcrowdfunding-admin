package com.mfh.crowd.funding.handler;

import com.mfh.crowd.funding.entity.Menu;
import com.mfh.crowd.funding.entity.ResultEntity;
import com.mfh.crowd.funding.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mfh
 * @date 2020/2/19 11:11
 */
@RestController
public class MenuHandler {
    private MenuService menuService;

    @RequestMapping("/menu/get/whole/tree")
    public ResultEntity<Menu> getAll() {
        List<Menu> menuList = menuService.getAll();
        Map<Integer, Menu> menuMap = new HashMap<>(menuList.size());
        for (Menu menu : menuList) {
            menuMap.put(menu.getId(), menu);
        }
        Menu root = null;
        for (Menu menu : menuList) {
            Integer pid = menu.getPid();
            if (pid == null) {
                root = menu;
                continue;
            }
            Menu parentMenu = menuMap.get(pid);
            parentMenu.getChildren().add(menu);
        }
        return ResultEntity.successWithData(root);
    }

    @RequestMapping("/menu/save")
    public ResultEntity<String> saveMenu(Menu menu) {
        menuService.saveMenu(menu);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/menu/get/{menuId}")
    public ResultEntity<Menu> getMenuById(@PathVariable Integer menuId) {
        Menu menu = menuService.getMenuById(menuId);
        return ResultEntity.successWithData(menu);
    }

    @RequestMapping("/menu/update")
    public ResultEntity<String> updateMenu(Menu menu) {
        menuService.updateMenu(menu);
        return ResultEntity.successWithoutData();
    }
    public MenuService getMenuService() {
        return menuService;
    }

    @Autowired
    public void setMenuService(MenuService menuService) {
        this.menuService = menuService;
    }
}
