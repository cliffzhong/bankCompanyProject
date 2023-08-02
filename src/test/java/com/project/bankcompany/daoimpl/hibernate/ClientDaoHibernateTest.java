package com.project.bankcompany.daoimpl.hibernate;


import com.project.bankcompany.dao.hibernate.ClientDao;
import com.project.bankcompany.entity.Client;
import com.project.bankcompany.entity.Manager;
import com.project.bankcompany.util.HibernateUtil;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

public class ClientDaoHibernateTest {

    private static ClientDaoHibernateImpl clientDao;
    private static ManagerDaoHibernateImpl managerDao;
    private static ProductDaoHibernateImpl productDao;

    @BeforeAll
    public static void initAll() {
        clientDao = new ClientDaoHibernateImpl();
        managerDao = new ManagerDaoHibernateImpl();
        productDao = new ProductDaoHibernateImpl();
    }

    @AfterAll
    public static void teardownAll() {
        clientDao = null;
        managerDao = null;
        productDao = null;
    }

    @BeforeEach
    public void setupEach() {
    }

    @AfterEach
    public void cleanupEach() {
    }

    @Test
    @Transactional
    public void testSaveClient() {
        Manager manager = createManagerByName("John Doe3");
        managerDao.save(manager);

        Client client = createClientByLoginName("client11", manager);
        clientDao.save(client, manager.getId());

        assertNotNull(client.getId());
        assertEquals("client11", client.getLoginName());
        assertEquals("First", client.getFirstName());
        assertEquals("Last", client.getLastName());
        assertEquals("client11@example.com", client.getEmail());
        assertEquals(manager.getId(), client.getManager().getId());
    }

    private Manager createManagerByName(String name) {
        Manager manager = new Manager();
        manager.setName(name);
        manager.setDescription(name + " is a manager.");
        return manager;
    }

    private Client createClientByLoginName(String loginName, Manager manager) {
        Client client = new Client();
        client.setLoginName(loginName);
        client.setPassword("password123");
        client.setFirstName("First");
        client.setLastName("Last");
        client.setEmail(loginName + "@example.com");
        client.setEnrolledDate(LocalDate.now());
        client.setManager(manager);
        return client;
    }

    @Test
    public void testGetClients() {
        List<Client> clientList = clientDao.getClients();
        assertEquals(3, clientList.size());
    }

    @Test
    public void testGetClientById() {
        Manager manager = createManagerByName("John Doe");
        managerDao.save(manager);

        Client client = createClientByLoginName("client1", manager);
        clientDao.save(client, manager.getId());

        Client retrievedClient = clientDao.getClientById(client.getId());
        assertNotNull(retrievedClient);
        assertEquals(client.getId(), retrievedClient.getId());
    }

    @Test
    public void testGetClientByLoginName() {
        Manager manager = createManagerByName("John Doe");
        managerDao.save(manager);

        Client client = createClientByLoginName("client1", manager);
        clientDao.save(client, manager.getId());

        Client retrievedClient = clientDao.getClientByLoginName(client.getLoginName());
        assertNotNull(retrievedClient);
        assertEquals(client.getLoginName(), retrievedClient.getLoginName());
    }

    @Test
    public void testUpdateClient() {
        Manager manager = createManagerByName("John Doee");
        managerDao.getManagerByName(manager.getName());

        Client client = clientDao.getClientByLoginName("client11");

        String newEmail = "updated_client11@example.com";
        client.setEmail(newEmail);

        clientDao.update(client);

        Client updatedClient = clientDao.getClientByLoginName(client.getLoginName());
        assertEquals(newEmail, updatedClient.getEmail());
    }

    @Test
    public void testDeleteClient() {
        Manager manager = createManagerByName("John Doe");
        managerDao.save(manager);

        Client client = clientDao.getClientByLoginName("client11");

        boolean deleteResult = clientDao.delete(client);
        assertTrue(deleteResult);

        Client deletedClient = clientDao.getClientById(client.getId());
        assertNull(deletedClient);
    }

    @Test
    public void testDeleteClientById() {
//        Manager manager = createManagerByName("John Doe");
//        managerDao.save(manager);

        Client client = clientDao.getClientById(20L);

        boolean deleteResult = clientDao.deleteById(client.getId());
        assertTrue(deleteResult);

        Client deletedClient = clientDao.getClientById(client.getId());
        assertNull(deletedClient);
    }
}
