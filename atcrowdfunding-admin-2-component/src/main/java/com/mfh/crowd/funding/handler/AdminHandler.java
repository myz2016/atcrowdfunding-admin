package com.mfh.crowd.funding.handler;

import com.github.pagehelper.PageInfo;
import com.mfh.crowd.funding.constants.CrowdFundingConstant;
import com.mfh.crowd.funding.entity.Admin;
import com.mfh.crowd.funding.entity.ResultEntity;
import com.mfh.crowd.funding.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author mfh
 * @date 2019/12/8 22:28
 */
@Controller
public class AdminHandler {

    private AdminService adminService;

    /**
     * 批量删除
     * 将当前handler方法的返回值作为响应体返回，不经过视图解析器
     * @param adminId
     * @return
     */
    @ResponseBody
    @RequestMapping("/admin/batch/remove")
    public ResultEntity<String> batchRemove(@RequestBody List<Integer> adminId, HttpServletRequest request) {
        try {
            System.out.println(10 / 0);
            adminService.batchRemove(adminId);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            return ResultEntity.failed(null, e.getMessage());
        }
    }
    @RequestMapping("/admin/query/for/search")
    public String queryForSearch(
            @RequestParam(value = "pageNum", defaultValue = "1")
            Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5")
            Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "")
            String keyword,
            Model model) {
        PageInfo<Admin> pageInfo = adminService.queryForKeywordSearch(pageNum, pageSize, keyword);
        model.addAttribute(CrowdFundingConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }
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
