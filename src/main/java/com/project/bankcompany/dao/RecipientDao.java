package com.project.bankcompany.dao;

import com.project.bankcompany.entity.CheckingTransaction;
import com.project.bankcompany.entity.Recipient;

import java.util.List;

public interface RecipientDao {

    Recipient save(Recipient recipient);

    Recipient update(Recipient recipient);

    boolean delete(Recipient recipient);

    boolean deleteById(Long id);

    boolean deleteByName(String name);

    List<Recipient> findAllRecipients();

    Recipient findById(Long id);

    Recipient findByName(String name);

}
