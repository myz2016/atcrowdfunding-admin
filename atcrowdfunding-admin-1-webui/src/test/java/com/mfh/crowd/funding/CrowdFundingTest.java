package com.mfh.crowd.funding;

import com.mfh.crowd.funding.entity.Admin;
import com.mfh.crowd.funding.entity.Role;
import com.mfh.crowd.funding.mapper.AdminMapper;
import com.mfh.crowd.funding.mapper.RoleMapper;
import com.mfh.crowd.funding.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author mfh
 * @date 2019/12/8 13:05
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdFundingTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;
    @Test
    public void saveRoleBatch() {
        for (int i = 0; i < 100; i++) {
            roleMapper.insert(new Role(i + 10, "roleName" + i));
        }
    }
    @Test
    public void testSelectAdminListByKeyword() {
        String keyword = "";
        List<Admin> admins = adminMapper.selectAdminListByKeyword(keyword);
        for (Admin admin : admins) {
            System.out.println(admin);
        }

    }
    @Test
    public void testConnection() throws SQLException {
        final Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testMyBatis() {
        List<Admin> list = adminService.selectAll();
        for (Admin admin : list) {
            System.out.println(admin);
        }
    }

    @Test
    public void testUpdateAdmin() {
//        adminService.updateAdmin();
    }
}
