package com.project.bankcompany.daoimpl.sproingdatajpa;


import com.project.bankcompany.dao.hibernate.ClientDao;
import com.project.bankcompany.daoimpl.repository.ClientRepository;
import com.project.bankcompany.entity.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ClientDaoSpringDataJPAImpl")
public class ClientDaoSpringDataJPAImpl implements ClientDao {

    private Logger logger = LoggerFactory.getLogger(ClientDaoSpringDataJPAImpl.class);

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client save(Client client, Long managerId) {
        Client savedClient = clientRepository.save(client);
        return savedClient;
    }

    @Override
    public Client update(Client client) {
        Client updateClient = clientRepository.save(client);
        return updateClient;
    }

    @Override
    public boolean deleteByLoginName(String loginName) {
        clientRepository.deleteByLoginName(loginName);
        return true;
    }

    @Override
    public boolean deleteById(Long clientId) {
        clientRepository.deleteById(clientId);
        return true;
    }

    @Override
    public boolean delete(Client client) {
        clientRepository.delete(client);
        return true;
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client getClientByLoginName(String loginName) {
        return clientRepository.findByLoginName(loginName);
    }

    @Override
    public List<Client> getClientsByManagerId(Long managerId) {
        return clientRepository.findClientsByManager_Id(managerId);
    }

    @Override
    public List<Client> getClientsWithAssociatedProducts() {
        return clientRepository.findClientsWithAssociatedProducts();
    }
}
