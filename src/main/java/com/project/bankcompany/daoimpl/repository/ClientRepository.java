package com.project.bankcompany.daoimpl.repository;


import com.project.bankcompany.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ClientRepository")
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Custom query to delete a client by login name
    void deleteByLoginName(String loginName);

    // Custom query to retrieve a client by login name
    Client findByLoginName(String loginName);

    // Custom query to retrieve clients associated with a specific manager
    @Query("SELECT c FROM Client c WHERE c.manager.id = :managerId")
    List<Client> findClientsByManager_Id(Long managerId);

    // Custom query to retrieve clients along with their associated products
    @Query("SELECT DISTINCT c FROM Client c LEFT JOIN FETCH c.productList")
    List<Client> findClientsWithAssociatedProducts();
}
