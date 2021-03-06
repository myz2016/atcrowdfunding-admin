package com.mfh.crowd.funding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mfh.crowd.funding.constants.CrowdFundingConstant;
import com.mfh.crowd.funding.entity.Admin;
import com.mfh.crowd.funding.entity.AdminExample;
import com.mfh.crowd.funding.mapper.AdminMapper;
import com.mfh.crowd.funding.service.api.AdminService;
import com.mfh.crowd.funding.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author mfh
 * @date 2019/12/7 22:38
 */
@Service
public class AdminServiceImpl implements AdminService {
    private AdminMapper mapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Admin> selectAll() {
        return mapper.selectByExample(new AdminExample());
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

    @Override
    public PageInfo<Admin> queryForKeywordSearch(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> admins = mapper.selectAdminListByKeyword(keyword);
        return new PageInfo<>(admins);
    }

    @Override
    public void batchRemove(List<Integer> adminId) {
        // QBC：Query By Criteria

        // 创建AdminExample对象（不要管Example单词是什么意思，它没有意思）
        AdminExample example = new AdminExample();

        // 创建Criteria对象（不要管Criteria单词是什么意思，它没有意思）
        // Criteria对象可以帮助我们封装查询条件
        // 通过使用Criteria对象，可以把Java代码转换成SQL语句中WHERE子句里面的具体查询条件
        AdminExample.Criteria criteria = example.createCriteria();

        // 针对要查询的字段封装具体的查询条件
        criteria.andIdIn(adminId);

        // 执行具体操作时把封装了查询条件的Example对象传入
        mapper.deleteByExample(example);
    }

    @Override
    public void saveAdmin(Admin admin) {
        this.encrypt(admin);
        try {
            mapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                throw new RuntimeException(CrowdFundingConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return mapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void updateAdmin(Admin admin) {
        this.encrypt(admin);
        mapper.updateByPrimaryKey(admin);
    }

    private void encrypt(Admin admin) {
        admin.setUserPswd(this.encrypt(admin.getUserPswd()));
    }

    /**
     * 加密
     *
     * @param password 加密前的密码
     * @return 加密后的密码
     */
    private String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    public AdminMapper getMapper() {
        return mapper;
    }

    @Autowired
    public void setMapper(AdminMapper mapper) {
        this.mapper = mapper;
    }
}
