/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.project.bankcompany.service.impl;


import com.project.bankcompany.dao.UserDao;
import com.project.bankcompany.dto.RoleDto;
import com.project.bankcompany.dto.UserDto;
import com.project.bankcompany.entity.Role;
import com.project.bankcompany.entity.User;
import com.project.bankcompany.service.UserService;
import com.project.bankcompany.util.DtoAndEntityConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    @Qualifier("userSpringDataJPADao")
    private UserDao userDao;

    public UserDto save(UserDto userDto) {
        User user = DtoAndEntityConvertUtil.convertUserDtoToUser(userDto);
        User saveduser =  userDao.save(user);
        UserDto saveduserDto = DtoAndEntityConvertUtil.convertUserToUserDto(saveduser);
        return saveduserDto;
    }

    public UserDto getUserByEmail(String email) {
        User user = userDao.getUserByEmail(email);
        UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
        return userDto;
    }

    public UserDto getUserById(Long userId) {
        User user = userDao.getUserById(userId);
        logger.info("======= Inside UserServiceImpl.java, getUserById(...) method, using userId={}, retrieved user={}", userId, user);
//        UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
        UserDto userDto = user.convertUserToUserDto();
        logger.info("======= Inside UserServiceImpl.java, getUserById(...) method, using userId={}, converted userDto={}", userId, userDto);
        return userDto;
    }

    public UserDto getUserByCredentials(String email, String password) {
        UserDto userDto = null;
        try {
            User user = userDao.getUserByCredentials(email, password);
            userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userDto;
    }

    public UserDto findByUsername(String username) {
        User user = userDao.findByUsername(username);
        UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
        return userDto;
    }

    public UserDto findByEmail(String email) {
        User user = userDao.findByEmail(email);
        UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
        return userDto;
    }

    public boolean checkUserExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }

        return false;
    }

    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }

        return false;
    }

    public List<UserDto> getAllUsers() {
        List<User> userList = userDao.findAllUsers();
        List<UserDto> userDtoList = getUserDtoListByUserList(userList);
        displayUserDtoList(userDtoList);
        return userDtoList;
    }

    private List<UserDto> getUserDtoListByUserList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : userList) {
            UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
//            UserDto userDto = user.convertUserToUserDto();
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    private void displayUserDtoList(List<UserDto> userDtoList) {
        int index = 1;
        for(UserDto userDto : userDtoList) {
            displayUserDto(index++, userDto);
        }
    }

    private void displayUserDto(int i, UserDto user) {
        logger.info("======= UserDto No.{} ========", i);
        logger.info("\t user.id={}", user.getId());
        logger.info("\t user.name={}", user.getUsername());
        logger.info("\t user.firstName={}", user.getFirstName());
        logger.info("\t user.lastName={}", user.getLastName());
        logger.info("\t user.email={}", user.getEmail());
        logger.info("\t user.secretKey={}", user.getSecretKey());
        Set<RoleDto> roleDtoList = user.getRoleDtoSet();
        displayRoleDtoList(roleDtoList);
    }

    private void displayRoleDtoList(Set<RoleDto> roleDtoSet) {
        int index = 1;
        for(RoleDto roleDto : roleDtoSet) {
            displayRoleDto(index++, roleDto);
        }
    }

    private void displayRoleDto(int i, RoleDto roleDto) {
        logger.info("\t######## RoleDto No.{} ##########", i);
        logger.info("\t\t roleDto={}", roleDto);
    }

    private void displayUsers(List<User> userList) {
        int index = 1;
        for(User user : userList) {
            displayUser(index++, user);
        }
    }

    private void displayUser(int i, User user) {
        logger.info("======= User No.{} ========", i);
        logger.info("\t user.id={}", user.getId());
        logger.info("\t user.name={}", user.getUsername());
        logger.info("\t user.firstName={}", user.getFirstName());
        logger.info("\t user.lastName={}", user.getLastName());
        logger.info("\t user.email={}", user.getEmail());
        logger.info("\t user.secretKey={}", user.getSecretKey());
        Set<Role> roleList = user.getRoles();
        displayRoles(roleList);
    }

    private void displayRoles(Set<Role> roleList) {
        int index = 1;
        for(Role role : roleList) {
            displayRole(index++, role);
        }
    }

    private void displayRole(int i, Role role) {
        logger.info("\t######## Role No.{} ##########", i);
        logger.info("\t\t role.id={}", role.getId());
        logger.info("\t\t role..name={}", role.getName());
        logger.info("\t\t role.allAllowedResource={}", role.getAllowedResource());
    }
}
