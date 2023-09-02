package com.project.bankcompany.daoimpl.repository;

import com.project.bankcompany.entity.CheckingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingTransactionRepository extends JpaRepository<CheckingTransaction, Long> {
}
