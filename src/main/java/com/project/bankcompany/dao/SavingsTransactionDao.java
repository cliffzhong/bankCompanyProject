package com.project.bankcompany.dao;

import com.project.bankcompany.entity.SavingsAccount;
import com.project.bankcompany.entity.SavingsTransaction;

import java.util.List;

public interface SavingsTransactionDao {

    SavingsTransaction save(SavingsTransaction savingsTransaction);

    SavingsTransaction update(SavingsTransaction savingsTransaction);

    boolean delete(SavingsTransaction savingsTransaction);

    boolean deleteById(Long id);

    List<SavingsTransaction> findAllSavingsTransactions();

    SavingsTransaction findById(Long id);
}
