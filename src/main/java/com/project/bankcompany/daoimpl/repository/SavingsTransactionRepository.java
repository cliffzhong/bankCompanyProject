package com.project.bankcompany.daoimpl.repository;

import com.project.bankcompany.entity.SavingsTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsTransactionRepository extends JpaRepository<SavingsTransaction, Long> {
}
