package com.mfh.crowd.funding.handler;

import com.mfh.crowd.funding.constants.CrowdFundingConstant;
import com.mfh.crowd.funding.entity.Admin;
import com.mfh.crowd.funding.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author mfh
 * @date 2019/12/8 22:28
 */
@Controller
public class AdminHandler {

    private AdminService adminService;

    @RequestMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index.html";
    }
    @RequestMapping("/admin/do/login")
    public String doLogin(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd, Model model, HttpSession session) {
        Admin admin = adminService.login(loginAcct, userPswd);
        if (null != admin) {
            session.setAttribute(CrowdFundingConstant.ATTR_NAME_LOGIN_ADMIN, admin);
            return "redirect:/admin/to/main/page.html";
        }
        model.addAttribute(CrowdFundingConstant.ATTR_NAME_MESSAGE, CrowdFundingConstant.MESSAGE_LOGIN_FAILED);
        return "admin-login";
    }
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
