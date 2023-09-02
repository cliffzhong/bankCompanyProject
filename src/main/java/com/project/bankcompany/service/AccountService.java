package com.project.bankcompany.service;

import com.project.bankcompany.dto.CheckingAccountDto;
import com.project.bankcompany.dto.SavingsAccountDto;

public interface AccountService {
    CheckingAccountDto createCheckingAccount(CheckingAccountDto checkingAccountDto);
    SavingsAccountDto createSavingsAccount(SavingsAccountDto savingsAccountDto);
    void deposit(String accountType, float amount, String username);
    void withdraw(String accountType, float amount, String username);


}
