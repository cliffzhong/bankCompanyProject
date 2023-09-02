package com.project.bankcompany.daoimpl.sproingdatajpa;

import com.project.bankcompany.dao.CheckingTransactionDao;
import com.project.bankcompany.daoimpl.repository.CheckingTransactionRepository;
import com.project.bankcompany.entity.CheckingTransaction;
import com.project.bankcompany.entity.SavingsAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("CheckingTransactionDaoSpringDataJPAImpl")
public class CheckingTransactionDaoSpringDataJPAImpl implements CheckingTransactionDao {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private CheckingTransactionRepository checkingTransactionRepository;

    @Override
    public CheckingTransaction save(CheckingTransaction checkingTransaction) {
        CheckingTransaction savedCheckingTransaction = checkingTransactionRepository.save(checkingTransaction);
        return savedCheckingTransaction;
    }

    @Override
    public CheckingTransaction update(CheckingTransaction checkingTransaction) {
        CheckingTransaction updatedCheckingTransaction = checkingTransactionRepository.save(checkingTransaction);
        return updatedCheckingTransaction;
    }

    @Override
    public boolean delete(CheckingTransaction checkingTransaction) {
        boolean deleteResult = false;
        try {
            checkingTransactionRepository.delete(checkingTransaction);
            deleteResult = true;
        } catch (IllegalArgumentException iae) {
            logger.error("Delete checkingTransaction failed, input checkingTransaction={}, error={}", checkingTransaction, iae.getMessage());
        }
        return deleteResult;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean successFlag = false;
        try{
            checkingTransactionRepository.deleteById(id);
            successFlag = true;
        }catch(IllegalArgumentException iae){
            logger.error("caught IllegalArgumentException when trying deleteById with checkingTransactionId={}, error={}",id, iae.getMessage());
        }catch(OptimisticLockingFailureException olfe){
            logger.error("caught OptimisticLockingFailureException when trying deleteById with checkingTransactionId={}, error={}",id, olfe.getMessage());
        }
        return successFlag;
    }

    @Override
    public List<CheckingTransaction> findAllCheckingTransactions() {
        List<CheckingTransaction> checkingTransactionList = checkingTransactionRepository.findAll();
        return checkingTransactionList;
    }

    @Override
    public CheckingTransaction findById(Long id) {
        CheckingTransaction checkingTransaction = null;
        Optional<CheckingTransaction> checkingTransactionOptional = checkingTransactionRepository.findById(id);
        if(checkingTransactionOptional.isPresent())
            checkingTransaction = checkingTransactionOptional.get();
        return checkingTransaction;
    }
}
