package com.mfh.crowd.funding.service.api;

import com.mfh.crowd.funding.entity.Admin;

import java.util.List;

/**
 * @author mfh
 * @date 2019/12/7 22:38
 */
public interface AdminService {
    List<Admin> selectAll();

    void updateAdmin();
}
