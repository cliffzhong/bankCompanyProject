package com.project.bankcompany.util;

import com.project.bankcompany.dto.RoleDto;
import com.project.bankcompany.dto.UserDto;
import com.project.bankcompany.entity.Role;
import com.project.bankcompany.entity.User;

import java.util.HashSet;
import java.util.Set;

public class DtoAndEntityConvertUtil {

    public static User convertUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setSecretKey(userDto.getSecretKey());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setRoles(getRolesByRoleDtoSet(userDto.getRoleDtoSet()));
        return user;
    }

    private static Set<Role> getRolesByRoleDtoSet(Set<RoleDto> roleDtoSet) {
        Set<Role> roleSet = new HashSet<>();
        for(RoleDto roleDto : roleDtoSet) {
            Role role = convertRoleDtoToRoleWithoutUser(roleDto);
            roleSet.add(role);
        }
        return roleSet;
    }

    private static Role convertRoleDtoToRoleWithoutUser(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        role.setAllowedResource(roleDto.getAllowedResource());
        role.setAllowedRead(roleDto.isAllowedRead());
        role.setAllowedCreate(roleDto.isAllowedCreate());
        role.setAllowedUpdate(roleDto.isAllowedUpdate());
        role.setAllowedDelete(roleDto.isAllowedDelete());
//        role.setUsers(getusersByUserDtoSet(roleDto.getUserDtoSet()));
        return role;
    }

    public static UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        userDto.setSecretKey(user.getSecretKey());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRoleDtoSet(getRoleDtoSetByRolesWithoutUserDto(user.getRoles()));
        return userDto;
    }

    private static Set<RoleDto> getRoleDtoSetByRolesWithoutUserDto(Set<Role> roles) {
        Set<RoleDto> roleDtoSet = new HashSet<>();
        for(Role role : roles) {
            RoleDto roleDto = convertRoleToRoleDtoWithoutUser(role);
            roleDtoSet.add(roleDto);
        }
        return roleDtoSet;
    }

    private static RoleDto convertRoleToRoleDtoWithoutUser(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setAllowedResource(role.getAllowedResource());
        roleDto.setAllowedRead(role.isAllowedRead());
        roleDto.setAllowedCreate(role.isAllowedCreate());
        roleDto.setAllowedUpdate(role.isAllowedUpdate());
        roleDto.setAllowedDelete(role.isAllowedDelete());
//        roleDto.setUserDtoSet(getUserDtoSetByUsers(role.getUsers()));
        return roleDto;
    }

    private static Set<RoleDto> getRoleDtoSetByRoles(Set<Role> roles) {
        Set<RoleDto> roleDtoSet = new HashSet<>();
        for(Role role : roles) {
            RoleDto roleDto = convertRoleToRoleDto(role);
            roleDtoSet.add(roleDto);
        }
        return roleDtoSet;
    }

    public static Role convertRoleDtoToRole(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        role.setAllowedResource(roleDto.getAllowedResource());
        role.setAllowedRead(roleDto.isAllowedRead());
        role.setAllowedCreate(roleDto.isAllowedCreate());
        role.setAllowedUpdate(roleDto.isAllowedUpdate());
        role.setAllowedDelete(roleDto.isAllowedDelete());
        role.setUsers(getusersByUserDtoSet(roleDto.getUserDtoSet()));
        return role;
    }

    private static Set<User> getusersByUserDtoSet(Set<UserDto> userDtoSet) {
        Set<User> userSet = new HashSet<>();
        for(UserDto userDto : userDtoSet) {
            User user = convertUserDtoToUser(userDto);
            userSet.add(user);
        }
        return userSet;
    }

    public static RoleDto convertRoleToRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        roleDto.setAllowedResource(role.getAllowedResource());
        roleDto.setAllowedRead(role.isAllowedRead());
        roleDto.setAllowedCreate(role.isAllowedCreate());
        roleDto.setAllowedUpdate(role.isAllowedUpdate());
        roleDto.setAllowedDelete(role.isAllowedDelete());
        roleDto.setUserDtoSet(getUserDtoSetByUsers(role.getUsers()));
        return roleDto;
    }

    private static Set<UserDto> getUserDtoSetByUsers(Set<User> users) {
        Set<UserDto> userDtoSet = new HashSet<>();
        for(User user : users) {
            UserDto userDto = convertUserToUserDtoWithoutRole(user);
            userDtoSet.add(userDto);
        }
        return userDtoSet;
    }

    public static UserDto convertUserToUserDtoWithoutRole(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        userDto.setSecretKey(user.getSecretKey());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
//        userDto.setRoleDtoSet(getRoleDtoSetByRolesWithoutUserDto(user.getRoles()));
        return userDto;
    }

}
