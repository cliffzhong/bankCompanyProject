package com.project.bankcompany.service.impl;


import com.project.bankcompany.dao.ClientDao;
import com.project.bankcompany.dao.ManagerDao;
import com.project.bankcompany.dao.ProductDao;
import com.project.bankcompany.dto.*;
import com.project.bankcompany.entity.*;
import com.project.bankcompany.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    @Qualifier("ManagerDaoSpringDataJPAImpl")
    private ManagerDao managerDao;

    @Autowired
    @Qualifier("ClientDaoSpringDataJPAImpl")
    private ClientDao clientDao;

    @Autowired
    @Qualifier("ProductDaoSpringDataJPAImpl")
    private ProductDao productDao;

    @Override
    public ClientDto signUp(ClientDto clientDto, Long managerId) {
        // Retrieve the manager by ID
        Manager manager = managerDao.getManagerById(managerId);
        if (manager == null) {
            throw new IllegalArgumentException("Manager not found");
        }

        // Associate the manager with the client
        clientDto.convertClientDtoToClient().setManager(manager);

        // Perform any other validation or processing you need before saving
        // For example, hashing the password before saving

        // Then call the DAO's save method
        return clientDao.save(clientDto.convertClientDtoToClient(), clientDto.getManager().getId()).convertClientToClientDto();
    }

    @Override
    public ClientDto signIn(String loginName, String password) {
        // Retrieve the client by login name from the DAO
        ClientDto clientDto = clientDao.getClientByLoginName(loginName).convertClientToClientDto();
        if (clientDto != null && clientDto.getPassword().equals(password)) {
            return clientDto;
        }
        return null; // Sign in failed
    }

    @Override
    public ClientDto save(ClientDto clientDto, Long managerId) {
        Client client = clientDto.convertClientDtoToClient();
        Client savedClient = clientDao.save(client,1L);
        ClientDto savedClientDto = savedClient.convertClientToClientDto();
        return savedClientDto;
    }

    @Override
    public ClientDto update(ClientDto managerDto) {
        Client client = managerDto.convertClientDtoToClient();
        Client updatedClient = clientDao.update(client);
        ClientDto updatedClientDto = updatedClient.convertClientToClientDto();
        return updatedClientDto;
    }

    @Override
    public boolean deleteByLoginName(String loginName) {
        Client client = clientDao.getClientByLoginName(loginName);
        if (client != null) {
            return clientDao.delete(client);
        }
        return false;
    }

    @Override
    public boolean deleteById(Long clientId) {
        Client client = clientDao.getClientById(clientId);
        if (client != null) {
            return clientDao.delete(client);
        }
        return false;
    }

    @Override
    public boolean delete(ClientDto clientDto) {
        Client client = clientDao.getClientById(clientDto.getId());
        if (client != null) {
            return clientDao.delete(client);
        }
        return false;
    }

    @Override
    public List<ClientDto> getClients() {
        List<Client> clients = clientDao.getClients();
        return getClientDtoListFromClientList(clients);
    }

    @Override
    public ClientDto getClientById(Long id) {
        Client client = clientDao.getClientById(id);
        return client != null ? client.convertClientToClientDto() : null;
    }

    @Override
    public ClientDto getClientByLoginName(String loginName) {
        Client client = clientDao.getClientByLoginName(loginName);
        return client != null ? client.convertClientToClientDto() : null;
    }

    private List<ClientDto> getClientDtoListFromClientList(List<Client> clients) {
        List<ClientDto> clientDtoList = new ArrayList<>();
        for (Client client : clients) {
            clientDtoList.add(client.convertClientToClientDto());
        }
        return clientDtoList;
    }
}
