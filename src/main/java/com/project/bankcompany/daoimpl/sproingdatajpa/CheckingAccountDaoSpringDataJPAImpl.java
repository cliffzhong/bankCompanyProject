package com.project.bankcompany.daoimpl.sproingdatajpa;

import com.project.bankcompany.dao.CheckingAccountDao;
import com.project.bankcompany.daoimpl.repository.CheckingAccountRepository;
import com.project.bankcompany.entity.CheckingAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CheckingAccountDaoSpringDataJPAImpl")
public class CheckingAccountDaoSpringDataJPAImpl implements CheckingAccountDao {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Override
    public CheckingAccount save(CheckingAccount checkingAccount) {
        CheckingAccount savedCheckingAccount = checkingAccountRepository.save(checkingAccount);
        return savedCheckingAccount;
    }

    @Override
    public CheckingAccount update(CheckingAccount checkingAccount) {
        CheckingAccount updatedCheckingAccount = checkingAccountRepository.save(checkingAccount);
        return updatedCheckingAccount;
    }

    @Override
    public boolean deleteById(Long checkingAccountId) {
        boolean successFlag = false;
        try{
            checkingAccountRepository.deleteById(checkingAccountId);
            successFlag = true;
        }catch(IllegalArgumentException iae){
            logger.error("caught IllegalArgumentException when trying deleteById with checkingAccountId={}, error={}",checkingAccountId, iae.getMessage());
        }catch(OptimisticLockingFailureException olfe){
            logger.error("caught OptimisticLockingFailureException when trying deleteById with checkingAccountId={}, error={}",checkingAccountId, olfe.getMessage());
        }
        return successFlag;
    }


    @Override
    public boolean delete(CheckingAccount checkingAccount) {
        boolean deleteResult = false;
        try {
            checkingAccountRepository.delete(checkingAccount);
            deleteResult = true;
        } catch (IllegalArgumentException iae) {
            logger.error("Delete User failed, input checkingAccount={}, error={}", checkingAccount, iae.getMessage());
        }
        return deleteResult;
    }

    @Override
    public List<CheckingAccount> findAllCheckingAccount() {
        List<CheckingAccount> checkingAccountList = checkingAccountRepository.findAll();
        return checkingAccountList;
    }

    @Override
    public CheckingAccount findCheckingAccountById(Long id) {
        CheckingAccount checkingAccount = checkingAccountRepository.getReferenceById(id);
        return checkingAccount;
    }
}
