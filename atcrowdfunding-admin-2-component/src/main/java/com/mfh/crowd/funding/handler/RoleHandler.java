package com.mfh.crowd.funding.handler;

import com.github.pagehelper.PageInfo;
import com.mfh.crowd.funding.entity.ResultEntity;
import com.mfh.crowd.funding.entity.Role;
import com.mfh.crowd.funding.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author mfh
 * @date 2020/2/17 10:45
 */
@Controller
public class RoleHandler {
    private RoleService roleService;

    @RequestMapping("/role/search/by/keyword")
    public ResultEntity<PageInfo<Role>> search(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        PageInfo<Role> info = roleService.queryForKeywordWithPage(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(info);
    }
    public RoleService getRoleService() {
        return roleService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
