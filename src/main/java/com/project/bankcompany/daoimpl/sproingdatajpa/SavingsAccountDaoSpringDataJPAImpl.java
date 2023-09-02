package com.project.bankcompany.daoimpl.sproingdatajpa;

import com.project.bankcompany.dao.SavingsAccountDao;
import com.project.bankcompany.daoimpl.repository.SavingsAccountRepository;
import com.project.bankcompany.entity.Manager;
import com.project.bankcompany.entity.SavingsAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("SavingsAccountDaoSpringDataJPAImpl")
public class SavingsAccountDaoSpringDataJPAImpl implements SavingsAccountDao {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;


    @Override
    public SavingsAccount save(SavingsAccount savingsAccount) {
        SavingsAccount savedSavingsAccount = savingsAccountRepository.save(savingsAccount);
        return savedSavingsAccount;
    }

    @Override
    public SavingsAccount update(SavingsAccount savingsAccount) {
        SavingsAccount updatedSavingsAccount = savingsAccountRepository.save(savingsAccount);
        return updatedSavingsAccount;
    }

    @Override
    public boolean delete(SavingsAccount savingsAccount) {
        boolean deleteResult = false;
        try {
            savingsAccountRepository.delete(savingsAccount);
            deleteResult = true;
        } catch (IllegalArgumentException iae) {
            logger.error("Delete User failed, input savingsAccount={}, error={}", savingsAccount, iae.getMessage());
        }
        return deleteResult;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean successFlag = false;
        try{
            savingsAccountRepository.deleteById(id);
            successFlag = true;
        }catch(IllegalArgumentException iae){
            logger.error("caught IllegalArgumentException when trying deleteById with savingsAccountId={}, error={}",id, iae.getMessage());
        }catch(OptimisticLockingFailureException olfe){
            logger.error("caught OptimisticLockingFailureException when trying deleteById with savingsAccountId={}, error={}",id, olfe.getMessage());
        }
        return successFlag;
    }

    @Override
    public List<SavingsAccount> findAllSavingsAccounts() {
        List<SavingsAccount> savingsAccountList = savingsAccountRepository.findAll();
        return savingsAccountList;
    }

    @Override
    public SavingsAccount findById(Long id) {
        SavingsAccount savingsAccount = null;
        Optional<SavingsAccount> savingsAccountOptional = savingsAccountRepository.findById(id);
        if(savingsAccountOptional.isPresent())
            savingsAccount = savingsAccountOptional.get();
        return savingsAccount;
    }
}
