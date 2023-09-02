package com.project.bankcompany.dao;

import com.project.bankcompany.entity.CheckingTransaction;

import java.util.List;

public interface CheckingTransactionDao {

    CheckingTransaction save(CheckingTransaction checkingTransaction);

    CheckingTransaction update(CheckingTransaction checkingTransaction);

    boolean delete(CheckingTransaction checkingTransaction);

    boolean deleteById(Long id);

    List<CheckingTransaction> findAllCheckingTransactions();

    CheckingTransaction findById(Long id);
}
