package com.mfh.crowd.funding.handler;

import com.mfh.crowd.funding.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author mfh
 * @date 2020/2/17 10:45
 */
@Controller
public class RoleHandler {
    private RoleService roleService;

    public RoleService getRoleService() {
        return roleService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
