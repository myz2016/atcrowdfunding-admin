package com.mfh.crowd.funding.service.impl;

import com.mfh.crowd.funding.config.SecurityAdmin;
import com.mfh.crowd.funding.entity.Admin;
import com.mfh.crowd.funding.entity.AdminExample;
import com.mfh.crowd.funding.entity.Auth;
import com.mfh.crowd.funding.entity.Role;
import com.mfh.crowd.funding.mapper.AdminMapper;
import com.mfh.crowd.funding.mapper.AuthMapper;
import com.mfh.crowd.funding.mapper.RoleMapper;
import com.mfh.crowd.funding.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mfh
 * @date 2020/12/26 10:11
 */
@Service
public class CrowdFundingUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andLoginAcctEqualTo(userName);

        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (!CrowdFundingUtils.collectionEffective(admins)) {
            return null;
        }
        Admin admin = admins.get(0);
        List<GrantedAuthority> authorityList = new ArrayList<>();

        // 查询当前 Admin 对应的角色
        List<Role> roles = roleMapper.selectAssignedRoleList(admin.getId());
        // 遍历
        for (Role role : roles) {
            // 注意：记得加前缀 ROLE_ !!!
            String roleName = "ROLE_" + role.getName();
            // 创建 SimpleGrantedAuthority 对象并加入集合
            authorityList.add(new SimpleGrantedAuthority(roleName));
        }

        // 查询当前 Admin 对应的权限
        List<Auth> auths = authMapper.selectAuthListByAdminId(admin.getId());
        // 遍历
        for (Auth auth : auths) {
            // 注意：这里不加前缀！！！
            String authName = auth.getName();
            // 如果不是有效字符串，则抛弃
            if (!CrowdFundingUtils.stringEffective(authName)) {
                continue;
            }
            // 创建 SimpleGrantedAuthority 对象并加入集合
            authorityList.add(new SimpleGrantedAuthority(authName));
        }

        // 返回 User 对象
        return new SecurityAdmin(admin, authorityList);
    }
}
