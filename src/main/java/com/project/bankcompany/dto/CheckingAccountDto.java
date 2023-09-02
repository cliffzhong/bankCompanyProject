package com.project.bankcompany.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bankcompany.entity.CheckingAccount;
import com.project.bankcompany.entity.CheckingTransaction;
import com.project.bankcompany.entity.SavingsTransaction;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckingAccountDto {

    private Long id;

    private String accountNumber;

    private BigDecimal accountBalance;

    private List<CheckingTransactionDto> checkingTransactionDtoList;

    public CheckingAccount convertCheckingAccountDtoToCheckingAccount() {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.setId(getId());
        checkingAccount.setAccountNumber(getAccountNumber());
        checkingAccount.setAccountBalance(getAccountBalance());
        checkingAccount.setCheckingTransactionList(getCheckingTransactionListByCheckingTransactionDtoList(getCheckingTransactionDtoList()));
        return checkingAccount;
    }

    private List<CheckingTransaction> getCheckingTransactionListByCheckingTransactionDtoList(List<CheckingTransactionDto> checkingTransactionDtoList) {
        List<CheckingTransaction> checkingTransactionList = new ArrayList<>();
        for(CheckingTransactionDto checkingTransactionDto : checkingTransactionDtoList) {
            CheckingTransaction checkingTransaction  = convertCheckingTransactionDtoToCheckingTransaction(checkingTransactionDto);
            checkingTransactionList.add(checkingTransaction);
        }
        return checkingTransactionList;
    }

    private CheckingTransaction convertCheckingTransactionDtoToCheckingTransaction(CheckingTransactionDto checkingTransactionDto){
        CheckingTransaction checkingTransaction = new CheckingTransaction();
        checkingTransaction.setId(checkingTransactionDto.getId());
        checkingTransaction.setAmount(checkingTransactionDto.getAmount());
        checkingTransaction.setDate(checkingTransactionDto.getDate());
        checkingTransaction.setDescription(checkingTransactionDto.getDescription());
        checkingTransaction.setStatus(checkingTransactionDto.getStatus());
        checkingTransaction.setType(checkingTransactionDto.getType());
        checkingTransaction.setCheckingAccount(checkingTransactionDto.getCheckingAccount());
        checkingTransaction.setAvailableBalance(checkingTransactionDto.getAvailableBalance());
        return checkingTransaction;
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

    public List<CheckingTransactionDto> getCheckingTransactionDtoList() {
        return checkingTransactionDtoList;
    }

    public void setCheckingTransactionDtoList(List<CheckingTransactionDto> checkingTransactionDtoList) {
        this.checkingTransactionDtoList = checkingTransactionDtoList;
    }
}
