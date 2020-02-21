package com.mfh.crowd.funding.handler;

import com.mfh.crowd.funding.entity.Auth;
import com.mfh.crowd.funding.entity.ResultEntity;
import com.mfh.crowd.funding.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author mfh
 * @date 2020/2/21 9:12
 */
@Controller
public class AuthHandler {
    private AuthService authService;

    @ResponseBody
    @RequestMapping("/assign/do/assign")
    public ResultEntity<String> doRoleAssignAuth(@RequestBody Map<String, List<Integer>> assignDataMap) {
        authService.updateRelationShipBetweenRoleAndAuth(assignDataMap);
        return ResultEntity.successWithoutData();
    }
    @ResponseBody
    @RequestMapping("/assign/get/all/auth")
    public ResultEntity<List<Auth>> getAllAuth() {
        List<Auth> authList = this.authService.getAllAuth();
        return ResultEntity.successWithData(authList);
    }

    @ResponseBody
    @RequestMapping("/assign/get/assign/auth")
    public ResultEntity<List<Integer>> getAssignedAuth(@RequestParam("roleId") Integer roleId) {
        List<Integer> assignedAuthList = authService.getAssignedAuthList(roleId);
        return ResultEntity.successWithData(assignedAuthList);
    }

    public AuthService getAuthService() {
        return authService;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
}
