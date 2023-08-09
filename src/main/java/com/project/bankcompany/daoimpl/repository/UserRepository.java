package com.project.bankcompany.daoimpl.repository;


import com.project.bankcompany.entity.User;
import com.project.bankcompany.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password) throws UserNotFoundException;

    User findByName(String name);
}
