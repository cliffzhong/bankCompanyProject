package com.project.bankcompany.controller;


import com.amazonaws.util.ValidationUtils;
import com.project.bankcompany.dto.*;
import com.project.bankcompany.entity.*;
import com.project.bankcompany.service.AccountService;
import com.project.bankcompany.service.TransactionService;
import com.project.bankcompany.service.UserService;
import org.apache.el.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping(value = "/checkingAccount", produces = MediaType.APPLICATION_JSON_VALUE)
    public CheckingAccountDto checkingAccount(@RequestParam("username") String username) {
        List<CheckingTransactionDto> checkingTransactionDtoList = transactionService.getAllCheckingTransactions(username);

        UserDto userDto = userService.findByUsername(username);
        CheckingAccountDto checkingAccountDto = userDto.getCheckingAccountDto();

        return checkingAccountDto;
    }

    @GetMapping(value = "/savingsAccount", produces = MediaType.APPLICATION_JSON_VALUE)
    public SavingsAccountDto savingsAccount(@RequestParam("username") String username) {
        List<SavingsTransactionDto> savingsTransactionDtoList = transactionService.getAllSavingsTransactions(username);

        UserDto userDto = userService.findByUsername(username);
        SavingsAccountDto savingsAccountDto = userDto.getSavingsAccountDto();

        return savingsAccountDto;
    }


    @PostMapping(value = "/deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> depositPOST(@RequestParam("amount") String amount, @RequestParam("accountType") String accountType, @RequestParam("username") String username) {
        accountService.deposit(accountType, Float.parseFloat(amount), username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PostMapping(value = "/withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> withdrawPOST(@RequestParam("amount") String amount, @RequestParam("accountType") String accountType, @RequestParam("username") String username) {
        accountService.withdraw(accountType, Float.parseFloat(amount), username);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
