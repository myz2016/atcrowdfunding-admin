package com.mfh.crowd.funding.config;

import com.mfh.crowd.funding.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 扩展 User 类
 * 创建 SecurityAdmin 对象时调用构造器，传入 originalAdmin 和 authorities
 * 可以通过 getOriginalAdmin() 方法获取原始的 Admin 对象
 * @author mfh
 * @date 2020/12/26 13:38
 */
public class SecurityAdmin extends User {
    private Admin originalAdmin;

    public SecurityAdmin(Admin admin, Collection<? extends GrantedAuthority> authorities) {
        super(admin.getUserName(), admin.getUserPswd(), true, true, true, true, authorities);
        this.originalAdmin = admin;
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }
}
