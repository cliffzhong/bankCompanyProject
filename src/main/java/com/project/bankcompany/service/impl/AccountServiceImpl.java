package com.project.bankcompany.service.impl;

import com.project.bankcompany.dao.CheckingAccountDao;
import com.project.bankcompany.dao.SavingsAccountDao;
import com.project.bankcompany.dao.UserDao;
import com.project.bankcompany.dto.*;
import com.project.bankcompany.entity.*;
import com.project.bankcompany.service.AccountService;
import com.project.bankcompany.service.TransactionService;
import com.project.bankcompany.service.UserService;
import com.project.bankcompany.util.DtoAndEntityConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Service("AccountServiceImpl")
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private CheckingAccountDao checkingAccountDao;

    @Autowired
    private SavingsAccountDao savingsAccountDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TransactionService transactionService;

    @Override
    public CheckingAccountDto createCheckingAccount(CheckingAccountDto checkingAccountDto) {
        CheckingAccount checkingAccount = DtoAndEntityConvertUtil.convertCheckingAccountDtoToCheckingAccount(checkingAccountDto);

        CheckingAccount savedCheckingAccount = checkingAccountDao.save(checkingAccount);
        CheckingAccountDto savedCheckingAccountDto = DtoAndEntityConvertUtil.convertCheckingAccountToCheckingAccountDto(savedCheckingAccount);
        return savedCheckingAccountDto;
    }

    @Override
    public SavingsAccountDto createSavingsAccount(SavingsAccountDto savingsAccountDto) {
        SavingsAccount savingsAccount = DtoAndEntityConvertUtil.convertSavingsAccountDtoToSavingsAccount(savingsAccountDto);
        SavingsAccount savedSavingsAccount = savingsAccountDao.save(savingsAccount);
        SavingsAccountDto savedSavingsAccountDto = DtoAndEntityConvertUtil.convertSavingsAccountToSavingsAccountDto(savedSavingsAccount);
        return savedSavingsAccountDto;
    }

    @Override
    public void deposit(String accountType, float amount, String username) {
        User user = userDao.getUserByUsername(username);
        UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);

        if (accountType.equalsIgnoreCase("Checking")) {
            CheckingAccountDto checkingAccountDto = userDto.getCheckingAccountDto();
            checkingAccountDto.setAccountBalance(checkingAccountDto.getAccountBalance().add(new BigDecimal(amount)));
            CheckingAccount checkingAccount = DtoAndEntityConvertUtil.convertCheckingAccountDtoToCheckingAccount(checkingAccountDto);
            checkingAccountDao.save(checkingAccount);

            LocalDate date = LocalDate.now();

            CheckingTransactionDto checkingTransactionDto = new CheckingTransactionDto(date, "Deposit to Checking Account", "Account", "Finished", amount, checkingAccount.getAccountBalance(), checkingAccount);
//            CheckingTransaction checkingTransaction = DtoAndEntityConvertUtil.convertCheckingTransactionDtoToCheckingTransaction(checkingTransactionDto);
            transactionService.saveCheckingDepositTransaction(checkingTransactionDto);


        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccountDto savingsAccountDto = userDto.getSavingsAccountDto();
            savingsAccountDto.setAccountBalance(savingsAccountDto.getAccountBalance().add(new BigDecimal(amount)));
            SavingsAccount savingsAccount = DtoAndEntityConvertUtil.convertSavingsAccountDtoToSavingsAccount(savingsAccountDto);
            savingsAccountDao.save(savingsAccount);

            LocalDate date = LocalDate.now();

            SavingsTransactionDto savingsTransactionDto = new SavingsTransactionDto(date, "Deposit to Savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
//            SavingsTransaction savingsTransaction = DtoAndEntityConvertUtil.convertSavingsTransactionDtoToSavingsTransaction(savingsTransactionDto);
            transactionService.saveSavingsDepositTransaction(savingsTransactionDto);

        }
    }

    @Override
    public void withdraw(String accountType, float amount, String username) {
        User user = userDao.getUserByUsername(username);
        UserDto userDto = user.convertUserToUserDto();

        if (accountType.equalsIgnoreCase("Checking")) {
            CheckingAccountDto checkingAccountDto = userDto.getCheckingAccountDto();
            checkingAccountDto.setAccountBalance(checkingAccountDto.getAccountBalance().subtract(new BigDecimal(amount)));
            CheckingAccount checkingAccount = DtoAndEntityConvertUtil.convertCheckingAccountDtoToCheckingAccount(checkingAccountDto);
            checkingAccountDao.save(checkingAccount);

            LocalDate date = LocalDate.now();

            CheckingTransactionDto checkingTransactionDto = new CheckingTransactionDto(date, "Withdraw to Checking Account", "Account", "Finished", amount, checkingAccount.getAccountBalance(), checkingAccount);
//            CheckingTransaction checkingTransaction = DtoAndEntityConvertUtil.convertCheckingTransactionDtoToCheckingTransaction(checkingTransactionDto);
            transactionService.saveCheckingWithdrawTransaction(checkingTransactionDto);


        } else if (accountType.equalsIgnoreCase("Savings")) {
            SavingsAccountDto savingsAccountDto = userDto.getSavingsAccountDto();
            savingsAccountDto.setAccountBalance(savingsAccountDto.getAccountBalance().subtract(new BigDecimal(amount)));
            SavingsAccount savingsAccount = DtoAndEntityConvertUtil.convertSavingsAccountDtoToSavingsAccount(savingsAccountDto);
            savingsAccountDao.save(savingsAccount);

            LocalDate date = LocalDate.now();

            SavingsTransactionDto savingsTransactionDto = new SavingsTransactionDto(date, "Withdraw to Savings Account", "Account", "Finished", amount, savingsAccount.getAccountBalance(), savingsAccount);
//            SavingsTransaction savingsTransaction = DtoAndEntityConvertUtil.convertSavingsTransactionDtoToSavingsTransaction(savingsTransactionDto);
            transactionService.saveSavingsWithdrawTransaction(savingsTransactionDto);

        }
    }
}
