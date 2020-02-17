package com.mfh.crowd.funding.service.api;

import com.github.pagehelper.PageInfo;
import com.mfh.crowd.funding.entity.Role;

/**
 * @author mfh
 * @date 2020/2/17 10:44
 */
public interface RoleService {
    PageInfo<Role> queryForKeywordWithPage(Integer pageNum, Integer pageSize, String keyword);
}
