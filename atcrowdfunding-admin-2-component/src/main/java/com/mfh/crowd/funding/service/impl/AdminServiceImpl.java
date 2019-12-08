package com.mfh.crowd.funding.service.impl;

import com.mfh.crowd.funding.entity.Admin;
import com.mfh.crowd.funding.entity.AdminExample;
import com.mfh.crowd.funding.mapper.AdminMapper;
import com.mfh.crowd.funding.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
