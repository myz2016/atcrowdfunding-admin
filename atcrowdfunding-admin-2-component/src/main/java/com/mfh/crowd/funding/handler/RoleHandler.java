package com.mfh.crowd.funding.handler;

import com.github.pagehelper.PageInfo;
import com.mfh.crowd.funding.entity.ResultEntity;
import com.mfh.crowd.funding.entity.Role;
import com.mfh.crowd.funding.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mfh
 * @date 2020/2/17 10:45
 */
@RestController
public class RoleHandler {
    private RoleService roleService;

    // SpEL 表达式
    // 拥有角色查询的权限
    @PreAuthorize("hasAuthority('role:get')")
    @RequestMapping("/role/search/by/keyword")
    public ResultEntity<PageInfo<Role>> search(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        PageInfo<Role> info = roleService.queryForKeywordWithPage(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(info);
    }

    @RequestMapping("role/get/list/by/id/list")
    public ResultEntity<List<Role>> getRoleListByRoleId(@RequestBody List<Integer> roleIdList) {
        List<Role> list = roleService.getRoleListByRoleId(roleIdList);
        return ResultEntity.successWithData(list);
    }

    @RequestMapping("/role/batch/remove")
    public ResultEntity<String> batchRemoveByRoleId(@RequestBody List<Integer> roleIdList) {
        roleService.batchRemoveByRoleId(roleIdList);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/role/save/role")
    public ResultEntity<String> saveRole(@RequestParam("roleName") String roleName) {
        roleService.saveRole(roleName);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/role/update/role")
    public ResultEntity<String> updateRole(@RequestParam("roleId") Integer roleId, @RequestParam("roleName") String roleName) {
        roleService.updateRole(roleId, roleName);
        return ResultEntity.successWithoutData();
    }
    public RoleService getRoleService() {
        return roleService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
