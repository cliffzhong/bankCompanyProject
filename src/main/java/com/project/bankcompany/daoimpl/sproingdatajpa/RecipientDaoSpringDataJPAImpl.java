package com.project.bankcompany.daoimpl.sproingdatajpa;

import com.project.bankcompany.dao.RecipientDao;
import com.project.bankcompany.daoimpl.repository.RecipientRepository;
import com.project.bankcompany.entity.Appointment;
import com.project.bankcompany.entity.Recipient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("RecipientDaoSpringDataJPAImpl")
public class RecipientDaoSpringDataJPAImpl implements RecipientDao {

    private Logger logger = LoggerFactory.getLogger(UserDaoSpringDataJPAImpl.class);

    @Autowired
    private RecipientRepository recipientRepository;

    @Override
    public Recipient save(Recipient recipient) {
        Recipient savedRecipient = recipientRepository.save(recipient);
        return savedRecipient;
    }

    @Override
    public Recipient update(Recipient recipient) {
        Recipient updatedRecipient = recipientRepository.save(recipient);
        return updatedRecipient;
    }

    @Override
    public boolean delete(Recipient recipient) {
        boolean deleteResult = false;
        try {
            recipientRepository.delete(recipient);
            deleteResult = true;
        } catch (IllegalArgumentException iae) {
            logger.error("Delete Recipient failed, input Recipient={}, error={}", recipient, iae.getMessage());
        }
        return deleteResult;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean successFlag = false;
        try{
            recipientRepository.deleteById(id);
            successFlag = true;
        }catch(IllegalArgumentException iae){
            logger.error("caught IllegalArgumentException when trying deleteById with RecipientId={}, error={}",id, iae.getMessage());
        }catch(OptimisticLockingFailureException olfe){
            logger.error("caught OptimisticLockingFailureException when trying deleteById with RecipientId={}, error={}",id, olfe.getMessage());
        }
        return successFlag;
    }

    @Override
    public List<Recipient> findAllRecipients() {
        List<Recipient> recipientList = recipientRepository.findAll();
        return recipientList;
    }

    @Override
    public Recipient findById(Long id) {
        Recipient recipient = null;
        Optional<Recipient> recipientOptional = recipientRepository.findById(id);
        if(recipientOptional.isPresent())
            recipient = recipientOptional.get();
        return recipient;
    }
}
