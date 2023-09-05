package com.project.bankcompany.dao;




import com.project.bankcompany.entity.Role;
import com.project.bankcompany.entity.User;
import com.project.bankcompany.exception.UserNotFoundException;

import java.util.List;

public interface UserDao {
    User save(User user);
    User getUserByEmail(String email);
    User getUserById(Long id);
    User getUserByCredentials(String email, String password) throws UserNotFoundException;
    User addRole(User user, Role role);
    boolean delete(User user);
    List<User> findAllUsers();
    User getUserByUsername(String username);

    User findByUsername(String username);
    User findByEmail(String email);
}
