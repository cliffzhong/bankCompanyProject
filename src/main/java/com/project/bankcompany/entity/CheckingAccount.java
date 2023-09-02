package com.project.bankcompany.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class CheckingAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    private BigDecimal accountBalance;

    @OneToMany(mappedBy = "checkingAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CheckingTransaction> checkingTransactionList;

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

    public List<CheckingTransaction> getCheckingTransactionList() {
        return checkingTransactionList;
    }

    public void setCheckingTransactionList(List<CheckingTransaction> checkingTransactionList) {
        this.checkingTransactionList = checkingTransactionList;
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", accountBalance=" + accountBalance +
                ", checkingTransactionList=" + checkingTransactionList +
                '}';
    }
}

