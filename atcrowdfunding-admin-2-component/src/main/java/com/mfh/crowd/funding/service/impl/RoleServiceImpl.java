package com.mfh.crowd.funding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mfh.crowd.funding.entity.Role;
import com.mfh.crowd.funding.mapper.RoleMapper;
import com.mfh.crowd.funding.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageInfo<Role> queryForKeywordWithPage(Integer pageNum, Integer pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<Role> roles = this.mapper.selectForKeywordSearch(keyword);
        return new PageInfo<>(roles);
    }
}
