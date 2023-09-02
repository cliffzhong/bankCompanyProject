package com.project.bankcompany.dao;

import com.project.bankcompany.entity.CheckingAccount;
import com.project.bankcompany.entity.Client;

import java.util.List;

public interface CheckingAccountDao {

    CheckingAccount save(CheckingAccount checkingAccount);

    CheckingAccount update(CheckingAccount checkingAccount);


    boolean deleteById(Long checkingAccountId);

    boolean delete(CheckingAccount checkingAccount);

    List<CheckingAccount> findAllCheckingAccount();

    CheckingAccount findCheckingAccountById(Long id);

}
