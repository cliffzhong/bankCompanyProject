package com.project.bankcompany.daoimpl.repository;


import com.project.bankcompany.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    void deleteByName(String productName);

    Product findByName(String productName);

    @Query("SELECT p FROM Product p WHERE p.clientList IS NOT EMPTY")
    List<Product> findAllByClientListIsNotNull();

    @Query("SELECT p FROM Product p WHERE p.id = ?1 AND p.clientList IS NOT EMPTY")
    Product findByIdAndClientListIsNotNull(Long productId);

    @Query("SELECT p FROM Product p WHERE p.name = ?1 AND p.clientList IS NOT EMPTY")
    Product findByNameAndClientListIsNotNull(String productName);
}
