package com.mfh.crowd.funding.service.impl;

import com.mfh.crowd.funding.entity.Auth;
import com.mfh.crowd.funding.entity.AuthExample;
import com.mfh.crowd.funding.mapper.AuthMapper;
import com.mfh.crowd.funding.service.api.AuthService;
import com.mfh.crowd.funding.util.CrowdFundingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author mfh
 * @date 2020/2/21 9:12
 */
@Service
public class AuthServiceImpl implements AuthService {
    private AuthMapper authMapper;
    @Override
    public List<Auth> getAllAuth() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignedAuthList(Integer roleId) {
        return authMapper.selectAssignedAuthList(roleId);
    }

    @Override
    public void updateRelationShipBetweenRoleAndAuth(Map<String, List<Integer>> assignDataMap) {

        // 1.获取两部分List数据
        List<Integer> roleIdList = assignDataMap.get("roleIdList");
        List<Integer> authIdList = assignDataMap.get("authIdList");

        // 2.取出roleId
        Integer roleId = roleIdList.get(0);

        // 3.删除旧数据
        authMapper.deleteOldRelationship(roleId);

        // 4.保存新数据
        if(CrowdFundingUtils.collectionEffective(authIdList)) {
            authMapper.insertNewRelationship(roleId, authIdList);
        }

    }

    @Autowired
    public void setAuthMapper(AuthMapper authMapper) {
        this.authMapper = authMapper;
    }
}
