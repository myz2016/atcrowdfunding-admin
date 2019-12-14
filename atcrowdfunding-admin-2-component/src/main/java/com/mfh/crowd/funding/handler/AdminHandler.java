package com.mfh.crowd.funding.handler;

import com.mfh.crowd.funding.entity.Admin;
import com.mfh.crowd.funding.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mfh
 * @date 2019/12/8 22:28
 */
@Controller
public class AdminHandler {

    private AdminService adminService;
    @RequestMapping("/admin/get/all")
    public String getAll(Model model) {
        final List<Admin> admins = adminService.selectAll();
        model.addAttribute("list", admins);
        return "admin-target";
    }

    public AdminService getAdminService() {
        return adminService;
    }

    @Autowired
    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }
}
