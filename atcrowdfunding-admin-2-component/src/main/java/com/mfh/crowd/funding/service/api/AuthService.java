package com.mfh.crowd.funding.service.api;

import com.mfh.crowd.funding.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @author mfh
 * @date 2020/2/21 9:10
 */
public interface AuthService {
    List<Auth> getAllAuth();
    List<Integer> getAssignedAuthList(Integer roleId);

    void updateRelationShipBetweenRoleAndAuth(Map<String, List<Integer>> assignDataMap);
}
