package com.project.bankcompany.daoimpl.repository;


import com.project.bankcompany.entity.Manager;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("ManagerRepository")
public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Long deleteByName(String name);



//    Long deleteByNameAndDescription(String name, String description);
//

    //    @Query("SELECT distinct m FROM manager as m left join fetch m.clients as client left join fetch client.products where m.id = :id")
//    Manager findManagerWithClientsAndProductsByManagerId(@Param(value = "id") Long managerId);

    Manager findByName(String managerName);

    @EntityGraph(attributePaths = { "clientList", "productList" })
    @Query("SELECT m FROM Manager m WHERE m.id = :id")
    Manager findByIdWithClientsAndProducts(Long id);

}
