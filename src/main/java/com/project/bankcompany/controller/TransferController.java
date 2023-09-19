package com.project.bankcompany.controller;

import com.project.bankcompany.dto.CheckingAccountDto;
import com.project.bankcompany.dto.RecipientDto;
import com.project.bankcompany.dto.SavingsAccountDto;
import com.project.bankcompany.dto.UserDto;
import com.project.bankcompany.entity.CheckingAccount;
import com.project.bankcompany.entity.Recipient;
import com.project.bankcompany.entity.User;
import com.project.bankcompany.service.TransactionService;
import com.project.bankcompany.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/betweenAccounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> betweenAccountsPost(@RequestParam("transferFrom") String transferFrom, @RequestParam("transferTo") String transferTo, @RequestParam("amount") String amount, @RequestParam("username") String username) throws Exception {

        UserDto userDto = userService.findByUsername(username);
        CheckingAccountDto checkingAccountDto = userDto.getCheckingAccountDto();
        SavingsAccountDto savingsAccountDto = userDto.getSavingsAccountDto();
        transactionService.betweenAccountsTransfer(transferFrom, transferTo, amount, checkingAccountDto, savingsAccountDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping(value = "/recipient", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RecipientDto> recipient(@RequestParam("username") String username) {
        List<RecipientDto> recipientDtoList = transactionService.getAllRecipients(username);

        return recipientDtoList;
    }

    @GetMapping(value="/recipient/{recipientName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RecipientDto findRecipientByName(@PathVariable("recipientName") String recipientName){
        RecipientDto recipientDto = transactionService.findRecipientByName(recipientName);
        return recipientDto;
    }


    @PostMapping(value = "/recipient/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> recipientPost(@RequestParam("username") String username, @RequestBody RecipientDto recipientDto) {


        UserDto userDto = userService.findByUsername(username);
        recipientDto.setUserDto(userDto);
        transactionService.saveRecipient(recipientDto);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/recipient/edit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> recipientEdit(@RequestParam(value = "recipientName") String recipientName, @RequestParam(value = "username") String username, @RequestBody RecipientDto updatedRecipientDto){

        RecipientDto recipientDto = transactionService.findRecipientByName(recipientName);
        recipientDto = updatedRecipientDto;
        List<RecipientDto> recipientDtoList = transactionService.getAllRecipients(username);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/toSomeoneElse", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> toSomeoneElsePost(@RequestParam("recipientName") String recipientName, @RequestParam("accountType") String accountType, @RequestParam("amount") String amount, @RequestParam("username") String username) {
        UserDto userDto = userService.findByUsername(username);
        RecipientDto recipientDto = transactionService.findRecipientByName(recipientName);
        transactionService.toSomeoneElseTransfer(recipientDto, accountType, amount, userDto.getCheckingAccountDto(), userDto.getSavingsAccountDto());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
