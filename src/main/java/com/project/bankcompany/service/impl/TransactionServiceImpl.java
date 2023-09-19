package com.project.bankcompany.service.impl;

import com.project.bankcompany.dao.*;
import com.project.bankcompany.dto.*;
import com.project.bankcompany.entity.*;
import com.project.bankcompany.service.TransactionService;
import com.project.bankcompany.service.UserService;
import com.project.bankcompany.util.DtoAndEntityConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        User user = userDao.getUserByUsername(username);
        UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
        List<SavingsTransactionDto> savingsTransactionDtoList = userDto.getSavingsAccountDto().getSavingsTransactionDtoList();
        return savingsTransactionDtoList;
    }

    @Override
    public CheckingTransactionDto saveCheckingDepositTransaction(CheckingTransactionDto checkingTransactionDto) {
        CheckingTransaction checkingTransaction = DtoAndEntityConvertUtil.convertCheckingTransactionDtoToCheckingTransaction(checkingTransactionDto);
        CheckingTransaction savedCheckingTransaction = checkingTransactionDao.save(checkingTransaction);
        CheckingTransactionDto savedCheckingTransactionDto = DtoAndEntityConvertUtil.convertCheckingTransactionToCheckingTransactionDto(savedCheckingTransaction);
        return savedCheckingTransactionDto;
    }

    @Override
    public SavingsTransactionDto saveSavingsDepositTransaction(SavingsTransactionDto savingsTransactionDto) {
        SavingsTransaction savingsTransaction = DtoAndEntityConvertUtil.convertSavingsTransactionDtoToSavingsTransaction(savingsTransactionDto);
        SavingsTransaction savedSavingsTransaction = savingsTransactionDao.save(savingsTransaction);
        SavingsTransactionDto savedSavingsTransactionDto = DtoAndEntityConvertUtil.convertSavingsTransactionToSavingsTransactionDto(savedSavingsTransaction);
        return savedSavingsTransactionDto;
    }

    @Override
    public CheckingTransactionDto saveCheckingWithdrawTransaction(CheckingTransactionDto checkingTransactionDto) {
        CheckingTransaction checkingTransaction = DtoAndEntityConvertUtil.convertCheckingTransactionDtoToCheckingTransaction(checkingTransactionDto);
        CheckingTransaction savedCheckingTransaction = checkingTransactionDao.save(checkingTransaction);
        CheckingTransactionDto savedCheckingTransactionDto = DtoAndEntityConvertUtil.convertCheckingTransactionToCheckingTransactionDto(savedCheckingTransaction);
        return savedCheckingTransactionDto;
    }

    @Override
    public SavingsTransactionDto saveSavingsWithdrawTransaction(SavingsTransactionDto savingsTransactionDto) {
        SavingsTransaction savingsTransaction = DtoAndEntityConvertUtil.convertSavingsTransactionDtoToSavingsTransaction(savingsTransactionDto);
        SavingsTransaction savedSavingsTransaction = savingsTransactionDao.save(savingsTransaction);
        SavingsTransactionDto savedSavingsTransactionDto = DtoAndEntityConvertUtil.convertSavingsTransactionToSavingsTransactionDto(savedSavingsTransaction);
        return savedSavingsTransactionDto;
    }

    @Override
    public void betweenAccountsTransfer(String transferFrom, String transferTo, String amount, CheckingAccountDto checkingAccountDto, SavingsAccountDto savingsAccountDto) throws Exception {
        CheckingAccount checkingAccount = DtoAndEntityConvertUtil.convertCheckingAccountDtoToCheckingAccount(checkingAccountDto);
        SavingsAccount savingsAccount = DtoAndEntityConvertUtil.convertSavingsAccountDtoToSavingsAccount(savingsAccountDto);


        if (transferFrom.equalsIgnoreCase("Checking") && transferTo.equalsIgnoreCase("Savings")) {

            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().add(new BigDecimal(amount)));
            checkingAccountDao.save(checkingAccount);
            savingsAccountDao.save(savingsAccount);

            LocalDate date = LocalDate.now();

            CheckingTransaction checkingTransaction = new CheckingTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Account", "Finished", Float.parseFloat(amount), checkingAccount.getAccountBalance(), checkingAccount);
            checkingTransactionDao.save(checkingTransaction);
        } else if (transferFrom.equalsIgnoreCase("Savings") && transferTo.equalsIgnoreCase("Checking")) {
            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().add(new BigDecimal(amount)));
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            checkingAccountDao.save(checkingAccount);
            savingsAccountDao.save(savingsAccount);

            LocalDate date = LocalDate.now();

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Between account transfer from "+transferFrom+" to "+transferTo, "Transfer", "Finished", Float.parseFloat(amount), savingsAccount.getAccountBalance(), savingsAccount);
            savingsTransactionDao.save(savingsTransaction);
        } else {
            throw new Exception("Invalid Transfer");
        }
    }

    @Override
    public List<RecipientDto> getAllRecipients(String username) {
        User user = userDao.getUserByUsername(username);
        UserDto userDto = DtoAndEntityConvertUtil.convertUserToUserDto(user);
        List<RecipientDto> recipientDtoList = userDto.getRecipientDtoList();
        return recipientDtoList;
    }

    @Override
    public RecipientDto saveRecipient(RecipientDto recipientDto) {
        Recipient recipient = DtoAndEntityConvertUtil.convertRecipientDtoToRecipient(recipientDto);
        Recipient savedRecipient = recipientDao.save(recipient);
        RecipientDto savedRecipientDto = DtoAndEntityConvertUtil.convertRecipientToRecipientDto(savedRecipient);
        return savedRecipientDto;
    }

    @Override
    public RecipientDto findRecipientByName(String recipientName) {
        Recipient recipient =  recipientDao.findByName(recipientName);
        RecipientDto savedRecipientDto = DtoAndEntityConvertUtil.convertRecipientToRecipientDto(recipient);
        return savedRecipientDto;
    }

    @Override
    public boolean deleteRecipientByName(String recipientName) {
        boolean deleteResult =  recipientDao.deleteByName(recipientName);
        return deleteResult;
    }

    @Override
    public boolean toSomeoneElseTransfer(RecipientDto recipientDto, String accountType, String amount, CheckingAccountDto checkingAccountDto, SavingsAccountDto savingsAccountDto) {
        CheckingAccount checkingAccount = DtoAndEntityConvertUtil.convertCheckingAccountDtoToCheckingAccount(checkingAccountDto);
        SavingsAccount savingsAccount = DtoAndEntityConvertUtil.convertSavingsAccountDtoToSavingsAccount(savingsAccountDto);
        boolean successFlag = false;
        if (accountType.equalsIgnoreCase("Checking")) {
            checkingAccount.setAccountBalance(checkingAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            checkingAccountDao.save(checkingAccount);

            LocalDate date = LocalDate.now();

            CheckingTransaction checkingTransaction = new CheckingTransaction(date, "Transfer to recipient "+recipientDto.getName(), "Transfer", "Finished", Float.parseFloat(amount), checkingAccount.getAccountBalance(), checkingAccount);
            checkingTransactionDao.save(checkingTransaction);
            successFlag = true;
        } else if (accountType.equalsIgnoreCase("Savings")) {
            savingsAccount.setAccountBalance(savingsAccount.getAccountBalance().subtract(new BigDecimal(amount)));
            savingsAccountDao.save(savingsAccount);

            LocalDate date = LocalDate.now();

            SavingsTransaction savingsTransaction = new SavingsTransaction(date, "Transfer to recipient "+recipientDto.getName(), "Transfer", "Finished", Float.parseFloat(amount), savingsAccount.getAccountBalance(), savingsAccount);
            savingsTransactionDao.save(savingsTransaction);
            successFlag = true;
        }
        return successFlag;
    }
}
