package com.mfh.crowd.funding.service.impl;

import com.mfh.crowd.funding.entity.Admin;
import com.mfh.crowd.funding.entity.AdminExample;
import com.mfh.crowd.funding.mapper.AdminMapper;
import com.mfh.crowd.funding.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mfh
 * @date 2020/12/26 10:11
 */
@Service
public class CrowdFundingUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andLoginAcctEqualTo(userName);

        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (!CrowdFundingUtils.collectionEffective(admins)) {
            return null;
        }
        Admin admin = admins.get(0);
        String password = admin.getUserPswd();
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("ROLE_学生", "ROLE_教授", "扫地");
        return new User(userName, password, authorityList);
    }
}
