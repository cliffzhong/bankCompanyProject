package com.project.bankcompany.daoimpl.sproingdatajpa;

import com.project.bankcompany.dao.SavingsTransactionDao;
import com.project.bankcompany.daoimpl.repository.SavingsTransactionRepository;
import com.project.bankcompany.entity.CheckingTransaction;
import com.project.bankcompany.entity.SavingsTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("SavingsTransactionDaoSpringDataJPAImpl")
public class SavingsTransactionDaoSpringDataJPAImpl implements SavingsTransactionDao {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private SavingsTransactionRepository savingsTransactionRepository;

    @Override
    public SavingsTransaction save(SavingsTransaction savingsTransaction) {
        SavingsTransaction savedSavingsTransaction = savingsTransactionRepository.save(savingsTransaction);
        return savedSavingsTransaction;
    }

    @Override
    public SavingsTransaction update(SavingsTransaction savingsTransaction) {
        SavingsTransaction updatedSavingsTransaction = savingsTransactionRepository.save(savingsTransaction);
        return updatedSavingsTransaction;
    }

    @Override
    public boolean delete(SavingsTransaction savingsTransaction) {
        boolean deleteResult = false;
        try {
            savingsTransactionRepository.delete(savingsTransaction);
            deleteResult = true;
        } catch (IllegalArgumentException iae) {
            logger.error("Delete savingsTransaction failed, input savingsTransaction={}, error={}", savingsTransaction, iae.getMessage());
        }
        return deleteResult;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean successFlag = false;
        try{
            savingsTransactionRepository.deleteById(id);
            successFlag = true;
        }catch(IllegalArgumentException iae){
            logger.error("caught IllegalArgumentException when trying deleteById with savingsTransactionId={}, error={}",id, iae.getMessage());
        }catch(OptimisticLockingFailureException olfe){
            logger.error("caught OptimisticLockingFailureException when trying deleteById with savingsTransactionId={}, error={}",id, olfe.getMessage());
        }
        return successFlag;
    }

    @Override
    public List<SavingsTransaction> findAllSavingsTransactions() {
        List<SavingsTransaction> savingsTransactionList = savingsTransactionRepository.findAll();
        return savingsTransactionList;
    }

    @Override
    public SavingsTransaction findById(Long id) {
        SavingsTransaction savingsTransaction = null;
        Optional<SavingsTransaction> savingsTransactionOptional = savingsTransactionRepository.findById(id);
        if(savingsTransactionOptional.isPresent())
            savingsTransaction = savingsTransactionOptional.get();
        return savingsTransaction;
    }
}
