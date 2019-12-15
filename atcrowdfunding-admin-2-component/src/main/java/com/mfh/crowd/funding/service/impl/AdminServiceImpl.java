package com.mfh.crowd.funding.service.impl;

import com.mfh.crowd.funding.entity.Admin;
import com.mfh.crowd.funding.entity.AdminExample;
import com.mfh.crowd.funding.mapper.AdminMapper;
import com.mfh.crowd.funding.service.api.AdminService;
import com.mfh.crowd.funding.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author mfh
 * @date 2019/12/7 22:38
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper mapper;

    @Override
    public List<Admin> selectAll() {
        return mapper.selectByExample(new AdminExample());
    }

    @Override
    public void updateAdmin() {
        mapper.updateByPrimaryKey(new Admin(308, "happy", "12345", "快乐", "happy@yeah.net", null));
        System.out.println(10 / 0);
        mapper.updateByPrimaryKey(new Admin(309, "sad", "12345", "难过", "happy@yeah.net", null));
    }

    @Override
    public Admin login(String loginAcct, String userPswd) {
        // 根据loginAcct查询数据库
        final AdminExample example = new AdminExample();
        example.createCriteria().andLoginAcctEqualTo(loginAcct);
        // 执行查询
        final List<Admin> admins = mapper.selectByExample(example);
        if (!CrowdFundingUtils.collectionEffective(admins)) {
            // 如果查询结果无效返回null
            return null;
        }
        // 获取唯一集合元素
        final Admin admin = admins.get(0);
        if (null == admin) {
            return null;
        }
        final String userPswdDataBase = admin.getUserPswd();
        String userPswdBrowser = CrowdFundingUtils.md5(userPswd);
        if (!Objects.equals(userPswdDataBase, userPswdBrowser)) {
            return null;
        }
        return admin;
    }
}
