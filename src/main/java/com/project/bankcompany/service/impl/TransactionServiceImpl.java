package com.project.bankcompany.service.impl;

import com.project.bankcompany.dao.*;
import com.project.bankcompany.dto.*;
import com.project.bankcompany.entity.CheckingTransaction;
import com.project.bankcompany.entity.User;
import com.project.bankcompany.service.TransactionService;
import com.project.bankcompany.service.UserService;
import com.project.bankcompany.util.DtoAndEntityConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TransactionServiceImpl")
public class TransactionServiceImpl implements TransactionService {
    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private UserDao userDao;

    @Autowired
    private CheckingTransactionDao checkingTransactionDao;

    @Autowired
    private SavingsTransactionDao savingsTransactionDao;

    @Autowired
    private CheckingAccountDao checkingAccountDao;

    @Autowired
    private SavingsAccountDao savingsAccountDao;

    @Autowired
    private RecipientDao recipientDao;


    @Override
    public List<CheckingTransactionDto> getAllCheckingTransactions(String username) {
        User user = userDao.getUserByUsername(username);
        UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
        List<CheckingTransactionDto> checkingTransactionDtoList = userDto.getCheckingAccountDto().getCheckingTransactionDtoList();
        return checkingTransactionDtoList;
    }

    @Override
    public List<SavingsTransactionDto> getAllSavingsTransactions(String username) {
        return null;
    }

    @Override
    public CheckingTransactionDto saveCheckingDepositTransaction(CheckingTransactionDto checkingTransactionDto) {
        return null;
    }

    @Override
    public SavingsTransactionDto saveSavingsDepositTransaction(SavingsTransactionDto savingsTransactionDto) {
        return null;
    }

    @Override
    public CheckingTransactionDto saveCheckingWithdrawTransaction(CheckingTransactionDto checkingTransactionDto) {
        return null;
    }

    @Override
    public SavingsTransactionDto saveSavingsWithdrawTransaction(SavingsTransactionDto savingsTransactionDto) {
        return null;
    }

    @Override
    public boolean betweenAccountsTransfer(String transferFrom, String transferTo, String amount, CheckingAccountDto checkingAccountDto, SavingsAccountDto savingsAccountDto) throws Exception {
        return false;
    }

    @Override
    public List<RecipientDto> getAllRecipients(String username) {
        return null;
    }

    @Override
    public RecipientDto saveRecipient(RecipientDto recipientDto) {
        return null;
    }

    @Override
    public RecipientDto findRecipientByName(String recipientName) {
        return null;
    }

    @Override
    public boolean deleteRecipientByName(String recipientName) {
        return false;
    }

    @Override
    public boolean toSomeoneElseTransfer(RecipientDto recipientDto, String accountType, String amount, CheckingAccountDto checkingAccountDto, SavingsAccountDto savingsAccountDto) {
        return false;
    }
}
