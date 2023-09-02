package com.project.bankcompany.dao;

import com.project.bankcompany.entity.Recipient;
import com.project.bankcompany.entity.SavingsAccount;

import java.util.List;

public interface SavingsAccountDao {

    SavingsAccount save(SavingsAccount savingsAccount);

    SavingsAccount update(SavingsAccount savingsAccount);

    boolean delete(SavingsAccount savingsAccount);

    boolean deleteById(Long id);

    List<SavingsAccount> findAllSavingsAccounts();

    SavingsAccount findById(Long id);

}
