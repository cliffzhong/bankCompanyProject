/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.project.bankcompany.service.impl;


import com.project.bankcompany.dao.RoleDao;
import com.project.bankcompany.dto.*;
import com.project.bankcompany.entity.*;
import com.project.bankcompany.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    @Qualifier("roleSpringDataJPADao")
    private RoleDao roleDao;

    public RoleDto getRoleByName(String name) {
        Role role = roleDao.getRoleByName(name);
//        RoleDto roleDto = DtoAndEntityConvertUtil.convertRoleToRoleDto(role);
        RoleDto roleDto = role.convertRoleToRoleDto();
        return roleDto;
    }

    public List<RoleDto> getAllRoles() {
        List<Role> roleList = roleDao.findAllRoles();
        List<RoleDto> roleDtoList = getRoleDtoListByRoleList(roleList);
        return roleDtoList;
    }

    private List<RoleDto> getRoleDtoListByRoleList(List<Role> roleList) {
        List<RoleDto> roleDtoList = new ArrayList<>();
        for(Role role : roleList) {
//            RoleDto roleDto = DtoAndEntityConvertUtil.convertRoleToRoleDto(role);
            RoleDto roleDto = role.convertRoleToRoleDto();
            roleDtoList.add(roleDto);
        }
        return roleDtoList;
    }
}
