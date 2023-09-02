package com.project.bankcompany.daoimpl.springdatajpa;

import com.project.bankcompany.dao.ClientDao;
import com.project.bankcompany.dao.ManagerDao;
import com.project.bankcompany.dao.ProductDao;
import com.project.bankcompany.entity.Client;
import com.project.bankcompany.entity.Manager;
import com.project.bankcompany.entity.Product;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ManagerDaoSpringDataJPATest {

    private Logger logger = LoggerFactory.getLogger(ManagerDaoSpringDataJPATest.class);

    @Autowired
    @Qualifier("ManagerDaoSpringDataJPAImpl")
    private ManagerDao managerDao;

    @Autowired
    @Qualifier("ClientDaoSpringDataJPAImpl")
    private ClientDao clientDao;

    @Autowired
    @Qualifier("ProductDaoSpringDataJPAImpl")
    private ProductDao productDao;

    @BeforeAll
    public static void setupAll() {
        // Perform setup actions before running any test methods
    }

    @AfterAll
    public static void cleanupAll() {
        // Perform cleanup actions after running all test methods
    }

    @BeforeEach
    public void setupEach() {
        // Perform setup actions before each test method
    }

    @AfterEach
    public void cleanupEach() {
        // Perform cleanup actions after each test method
    }

    @Test
    public void testSaveManager() {
        Manager manager = createManagerByName("John Doe");
        Manager savedManager = managerDao.save(manager);
        assertNotNull(savedManager.getId());
        assertEquals(manager.getId(), savedManager.getId());
        assertEquals(manager.getDescription(), savedManager.getDescription());
        assertEquals(manager.getName(), savedManager.getName());
    }

    private Manager createManagerByName(String name) {
        Manager manager = new Manager();
        manager.setName(name);
        manager.setDescription(name + "edc");
        return manager;
    }

    @Test
    public void testGetManagers() {
        List<Manager> managerList = managerDao.getManagers();
        assertEquals(7, managerList.size());
    }

    @Test
    public void testGetManagerById() {
        Manager manager = createManagerByName("Alice");
        managerDao.save(manager);

        Long managerId = manager.getId();
        Manager retrievedManager = managerDao.getManagerById(managerId);
        assertNotNull(retrievedManager);
        assertEquals(manager.getName(), retrievedManager.getName());
        assertEquals(manager.getDescription(), retrievedManager.getDescription());
    }

    @Test
    public void testDeleteManager() {
        Manager manager = managerDao.getManagerById(18L);

        Long managerId = manager.getId();
        assertTrue(managerDao.deleteById(managerId));

        Manager retrievedManager = managerDao.getManagerById(managerId);
        assertNull(retrievedManager);
    }

    @Test
    public void testGetManagerByName() {
        // Prepare test data
        String managerName = "Test Manager";
        Manager manager = createManager(managerName);
        managerDao.save(manager);

        // Perform the test
        Manager retrievedManager = managerDao.getManagerByName(managerName);

        assertNotNull(retrievedManager);
        assertEquals(manager.getId(), retrievedManager.getId());
        assertEquals(manager.getName(), retrievedManager.getName());
    }

    @Test
    public void testGetManagerAndClientAndProductWithManagerId() {
        // Prepare test data
        Manager manager = createManager("Test Manager");
        Client client1 = createClient("Client 1");
        Client client2 = createClient("Client 2");
        Product product1 = createProduct("Product 1");
        Product product2 = createProduct("Product 2");

        // Set associations
        manager.getClientList().add(client1);
        manager.getClientList().add(client2);
        client1.setManager(manager);
        client2.setManager(manager);
        client1.getProductList().add(product1);
        client2.getProductList().add(product2);
        product1.getClientList().add(client1);
        product2.getClientList().add(client2);

        // Save entities
        managerDao.save(manager);

        // Perform the test
        Manager retrievedManager = managerDao.getManagerAndClientAndProductWithManagerId(manager.getId());

        assertNotNull(retrievedManager);
        assertEquals(manager.getId(), retrievedManager.getId());
        assertEquals(manager.getName(), retrievedManager.getName());

        List<Client> retrievedClients = retrievedManager.getClientList();
        assertEquals(2, retrievedClients.size());
        assertTrue(retrievedClients.contains(client1));
        assertTrue(retrievedClients.contains(client2));

        List<Product> retrievedProducts1 = retrievedClients.get(0).getProductList();
        List<Product> retrievedProducts2 = retrievedClients.get(1).getProductList();
        assertEquals(1, retrievedProducts1.size());
        assertEquals(1, retrievedProducts2.size());
        assertTrue(retrievedProducts1.contains(product1) || retrievedProducts1.contains(product2));
        assertTrue(retrievedProducts2.contains(product1) || retrievedProducts2.contains(product2));
    }

    private Manager createManager(String name) {
        Manager manager = new Manager();
        manager.setName(name);
        return manager;
    }

    private Client createClient(String name) {
        Client client = new Client();
        client.setLoginName(name);
        return client;
    }

    private Product createProduct(String name) {
        Product product = new Product();
        product.setName(name);
        return product;
    }
}

