package com.project.bankcompany.service;




import com.project.bankcompany.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto);

    UserDto getUserByEmail(String email);

    UserDto getUserById(Long userid);

    UserDto getUserByCredentials(String email, String password);

    boolean checkUserExists(String username, String email);

    boolean checkUsernameExists(String username);

    boolean checkEmailExists(String email);

    UserDto findByUsername(String username);

    UserDto findByEmail(String email);

    List<UserDto> getAllUsers();


}
