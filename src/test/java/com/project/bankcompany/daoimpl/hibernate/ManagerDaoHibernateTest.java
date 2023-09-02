package com.project.bankcompany.daoimpl.hibernate;


import com.project.bankcompany.entity.Client;
import com.project.bankcompany.entity.Manager;
import com.project.bankcompany.entity.Product;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
public class ManagerDaoHibernateTest {

    private Logger logger = LoggerFactory.getLogger(ManagerDaoHibernateTest.class);

    private static ManagerDaoHibernateImpl managerDao;
    private static ClientDaoHibernateImpl clientDao;
    private static ProductDaoHibernateImpl productDao;

    @BeforeAll
    public static void initAll() {
        managerDao = new ManagerDaoHibernateImpl();
        clientDao = new ClientDaoHibernateImpl();
        productDao = new ProductDaoHibernateImpl();
    }

    @AfterAll
    public static void teardownAll() {
        managerDao = null;
        clientDao = null;
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
    public void testSaveManager() {
        Manager manager = createManagerByName("John Doe");
        Manager savedManager = managerDao.save(manager);
        assertNotNull(savedManager.getId());
        assertEquals(manager.getDescription(), savedManager.getDescription());
        assertEquals(manager.getName(), savedManager.getName());
    }

    private Manager createManagerByName(String name) {
        Manager manager = new Manager();
        manager.setName(name);
        manager.setDescription(name + " is a manager.");
        return manager;
    }

    @Test
    public void testGetManagers() {
        List<Manager> managerList = managerDao.getManagers();
        assertEquals(7, managerList.size());
    }

    @Test
    public void testGetManagerById() {
        Manager manager = createManagerByName("Jane Smith");
        managerDao.save(manager);

        Manager retrievedManager = managerDao.getManagerById(manager.getId());
        assertNotNull(retrievedManager);
        assertEquals(manager.getId(), retrievedManager.getId());
        managerDao.deleteByName(manager.getName());
    }

    @Test
    public void testGetManagerByName() {
        Manager manager = createManagerByName("Jane Smith");
        managerDao.save(manager);

        Manager retrievedManager = managerDao.getManagerByName(manager.getName());
        assertNotNull(retrievedManager);
        assertEquals(manager.getName(), retrievedManager.getName());
    }

    @Test
    public void testUpdateManager() {
        Manager manager = createManagerByName("John Doe");
        managerDao.save(manager);

        String newDescription = "Updated description";
        manager.setDescription(newDescription);

        Manager updatedManager = managerDao.update(manager);
        assertEquals(newDescription, updatedManager.getDescription());
    }

    @Test
    public void testDeleteManager() {
        Manager manager = createManagerByName("John Doe");
        managerDao.save(manager);

        boolean deleteResult = managerDao.delete(manager);
        assertTrue(deleteResult);

        Manager deletedManager = managerDao.getManagerById(manager.getId());
        assertNull(deletedManager);
    }

    @Test
    public void testDeleteManagerById() {
        Manager manager = managerDao.getManagerById(16L);

        boolean deleteResult = managerDao.deleteById(manager.getId());
        assertTrue(deleteResult);

        Manager deletedManager = managerDao.getManagerById(manager.getId());
        assertNull(deletedManager);
    }

    @Test
    public void testDeleteManagerByName() {
        Manager manager = createManagerByName("John Doe");
        managerDao.getManagerByName(manager.getName());

        boolean deleteResult = managerDao.deleteByName(manager.getName());
        assertTrue(deleteResult);

        Manager deletedManager = managerDao.getManagerById(manager.getId());
        assertNull(deletedManager);
    }

    @Test
    public void testGetManagerAndClientAndProductWithManagerId() {
        Manager manager = createManagerByName("Jane Smith");
        managerDao.save(manager);

        Client client1 = createClientByLoginName("client1", manager);
        clientDao.save(client1,1L);

        Client client2 = createClientByLoginName("client2", manager);
        clientDao.save(client2,2L);

        Product product1 = createProductByName("Product 1");
        productDao.save(product1);

        Product product2 = createProductByName("Product 2");
        productDao.save(product2);

        client1.addProduct(product1);
        client1.addProduct(product2);
        client2.addProduct(product1);

        Manager retrievedManager = managerDao.getManagerAndClientAndProductWithManagerId(manager.getId());
        assertNotNull(retrievedManager);
        assertEquals(2, retrievedManager.getClientList().size());
        assertEquals("client1", retrievedManager.getClientList().get(0).getLoginName());
        assertEquals("client2", retrievedManager.getClientList().get(1).getLoginName());
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

    private Product createProductByName(String name) {
        Product product = new Product();
        product.setName(name);
        product.setDescription("Description of " + name);
        product.setCreateDate(LocalDate.now());
        return product;
    }
}
