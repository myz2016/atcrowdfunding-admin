package com.mfh.crowd.funding.service.impl;

import com.mfh.crowd.funding.mapper.MenuMapper;
import com.mfh.crowd.funding.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mfh
 * @date 2020/2/19 11:11
 */
@Service
public class MenuServiceImpl implements MenuService {
    private MenuMapper mapper;

    public MenuMapper getMapper() {
        return mapper;
    }

    @Autowired
    public void setMapper(MenuMapper mapper) {
        this.mapper = mapper;
    }
}
