package com.project.bankcompany.service;




import com.project.bankcompany.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto);

    UserDto getUserByEmail(String email);

    UserDto getUserById(Long userid);

    UserDto getUserByCredentials(String email, String password);

    List<UserDto> getAllUsers();


}
