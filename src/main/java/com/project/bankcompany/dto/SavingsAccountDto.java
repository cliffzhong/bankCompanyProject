package com.project.bankcompany.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bankcompany.entity.Recipient;
import com.project.bankcompany.entity.SavingsAccount;
import com.project.bankcompany.entity.SavingsTransaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SavingsAccountDto {

    private Long id;
    private String accountNumber;
    private BigDecimal accountBalance;
    @JsonIgnore
    private List<SavingsTransactionDto> savingsTransactionDtoList;


    public SavingsAccount convertSavingsAccountDtoToSavingsAccount() {
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId(getId());
        savingsAccount.setAccountBalance(getAccountBalance());
        savingsAccount.setAccountNumber(getAccountNumber());
        savingsAccount.setSavingsTransactionList(getSavingsTransactionListBySavingsTransactionDtoList(getSavingsTransactionDtoList()));
        return savingsAccount;
    }

    private List<SavingsTransaction> getSavingsTransactionListBySavingsTransactionDtoList(List<SavingsTransactionDto> savingsTransactionDtoList) {
        List<SavingsTransaction> savingsTransactionList = new ArrayList<>();
        for(SavingsTransactionDto savingsTransactionDto : savingsTransactionDtoList) {
            SavingsTransaction savingsTransaction = convertSavingsTransactionDtoToSavingsTransaction(savingsTransactionDto);
            savingsTransactionList.add(savingsTransaction);
        }
        return savingsTransactionList;
    }

    private SavingsTransaction convertSavingsTransactionDtoToSavingsTransaction(SavingsTransactionDto savingsTransactionDto){
        SavingsTransaction savingsTransaction = new SavingsTransaction();
        savingsTransaction.setId(savingsTransactionDto.getId());
        savingsTransaction.setAmount(savingsTransactionDto.getAmount());
        savingsTransaction.setDate(savingsTransactionDto.getDate());
        savingsTransaction.setDescription(savingsTransactionDto.getDescription());
        savingsTransaction.setStatus(savingsTransactionDto.getStatus());
        savingsTransaction.setType(savingsTransactionDto.getType());
        savingsTransaction.setSavingsAccount(savingsTransactionDto.getSavingsAccount());
        savingsTransaction.setAvailableBalance(savingsTransactionDto.getAvailableBalance());
        return savingsTransaction;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public List<SavingsTransactionDto> getSavingsTransactionDtoList() {
        return savingsTransactionDtoList;
    }

    public void setSavingsTransactionDtoList(List<SavingsTransactionDto> savingsTransactionDtoList) {
        this.savingsTransactionDtoList = savingsTransactionDtoList;
    }
}
