package com.project.bankcompany.service;

import com.project.bankcompany.dto.*;
import com.project.bankcompany.entity.CheckingTransaction;
import com.project.bankcompany.entity.Recipient;
import com.project.bankcompany.entity.SavingsTransaction;

import java.util.List;

public interface TransactionService {

    List<CheckingTransactionDto> getAllCheckingTransactions(String username);

    List<SavingsTransactionDto> getAllSavingsTransactions(String username);

    CheckingTransactionDto saveCheckingDepositTransaction(CheckingTransactionDto checkingTransactionDto);

    SavingsTransactionDto saveSavingsDepositTransaction(SavingsTransactionDto savingsTransactionDto);

    CheckingTransactionDto saveCheckingWithdrawTransaction(CheckingTransactionDto checkingTransactionDto);

    SavingsTransactionDto saveSavingsWithdrawTransaction(SavingsTransactionDto savingsTransactionDto);

    void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, CheckingAccountDto checkingAccountDto, SavingsAccountDto savingsAccountDto) throws Exception;

    List<RecipientDto> getAllRecipients(String username);

    RecipientDto saveRecipient(RecipientDto recipientDto);

    RecipientDto findRecipientByName(String recipientName);

    boolean deleteRecipientByName(String recipientName);

    boolean toSomeoneElseTransfer(RecipientDto recipientDto, String accountType, String amount, CheckingAccountDto checkingAccountDto, SavingsAccountDto savingsAccountDto);
}
