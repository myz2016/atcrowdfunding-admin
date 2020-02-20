package com.mfh.crowd.funding.handler;

import com.mfh.crowd.funding.entity.Role;
import com.mfh.crowd.funding.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author mfh
 * @date 2020/2/20 12:59
 */
@Controller
public class AssignHandler {
    private RoleService roleService;

    @RequestMapping("/assign/role")
    public String doAssignRole(@RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList,
                               @RequestParam("adminId") Integer adminId,
                               @RequestParam("pageNum") Integer pageNum) {
        roleService.updateRelationship(roleIdList, adminId);
        return "redirect:/admin/query/for/search.html?pageNum=" + pageNum;
    }
    @RequestMapping("/assign/to/assign/role/page")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId, Model model) {
        List<Role> assignedRoleList = roleService.getAssignedRoleList(adminId);
        List<Role> unAssignedRoleList = roleService.getUnAssignedRoleList(adminId);
        model.addAttribute("assignedRoleList", assignedRoleList);
        model.addAttribute("unAssignedRoleList", unAssignedRoleList);
        return "assign-role";
    }

    public RoleService getRoleService() {
        return roleService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
