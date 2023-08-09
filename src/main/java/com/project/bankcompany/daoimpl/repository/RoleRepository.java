package com.project.bankcompany.daoimpl.repository;

import com.project.bankcompany.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
