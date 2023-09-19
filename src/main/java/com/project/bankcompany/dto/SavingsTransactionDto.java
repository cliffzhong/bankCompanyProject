package com.project.bankcompany.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.bankcompany.entity.SavingsAccount;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingsTransactionDto {
    public SavingsTransactionDto() {}

    public SavingsTransactionDto(LocalDate date, String description, String type, String status, float amount, BigDecimal availableBalance, SavingsAccount savingsAccount) {
        this.date = date;
        this.description = description;
        this.type = type;
        this.status = status;
        this.amount = amount;
        this.availableBalance = availableBalance;
        this.savingsAccount = savingsAccount;
    }

    private Long id;
    private LocalDate date;
    private String description;
    private String type;
    private String status;
    private float amount;
    private BigDecimal availableBalance;
    @JsonIgnore
    private SavingsAccount savingsAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public SavingsAccount getSavingsAccount() {
        return savingsAccount;
    }

    public void setSavingsAccount(SavingsAccount savingsAccount) {
        this.savingsAccount = savingsAccount;
    }


}
