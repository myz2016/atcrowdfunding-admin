package com.mfh.crowd.funding.service.impl;

import com.mfh.crowd.funding.mapper.RoleMapper;
import com.mfh.crowd.funding.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mfh
 * @date 2020/2/17 10:44
 */
@Service
public class RoleServiceImpl implements RoleService {
    private RoleMapper mapper;

    public RoleMapper getMapper() {
        return mapper;
    }

    @Autowired
    public void setMapper(RoleMapper mapper) {
        this.mapper = mapper;
    }
}
