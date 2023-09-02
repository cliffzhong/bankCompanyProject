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
public class ClientDaoSpringDataJPATest {

    private Logger logger = LoggerFactory.getLogger(ClientDaoSpringDataJPATest.class);

    @Autowired
    @Qualifier("ClientDaoSpringDataJPAImpl")
    private ClientDao clientDao;

    @Autowired
    @Qualifier("ManagerDaoSpringDataJPAImpl")
    private ManagerDao managerDao;

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
    public void testSaveClient() {
        Client client = createClientByLoginName("john_doe");
        Manager manager = managerDao.getManagerById(1L);
        client.setManager(manager);
        Client savedClient = clientDao.save(client, manager.getId());
        assertNotNull(savedClient.getId());
        assertEquals(client.getId(), savedClient.getId());
        assertEquals(client.getLoginName(), savedClient.getLoginName());
        assertEquals(client.getManager().getId(), savedClient.getManager().getId());
    }

    private Client createClientByLoginName(String loginName) {
        Client client = new Client();
        client.setLoginName(loginName);
        return client;
    }

    @Test
    public void testUpdateClient() {
        Client client = createClientByLoginName("john_doe");
        Manager manager = managerDao.getManagerById(1L);
        client.setManager(manager);
        Client savedClient = clientDao.save(client, manager.getId());
        savedClient.setLoginName("updated_login_name");
        Client updatedClient = clientDao.update(savedClient);
        assertEquals(savedClient.getId(), updatedClient.getId());
        assertEquals("updated_login_name", updatedClient.getLoginName());
    }

    private Manager createManagerByName(String name) {
        Manager manager = new Manager();
        manager.setName(name);
        manager.setDescription(name + " description");
        return manager;
    }


    @Test
    public void testDeleteByLoginName() {
        // Test deleting a client by login name
        Client client = createClientByLoginName("john_doe");
        Manager manager = createManagerByName("Manager1");
        managerDao.save(manager);
        client.setManager(manager);
        Client savedClient = clientDao.save(client, manager.getId());

        boolean deleteResult = clientDao.deleteByLoginName(savedClient.getLoginName());
        assertTrue(deleteResult);
        Client deletedClient = clientDao.getClientById(savedClient.getId());
        assertNull(deletedClient);
    }

    @Test
    public void testDeleteById() {
        // Test deleting a client by ID
        Client client = createClientByLoginName("john_doe");
        Manager manager = createManagerByName("Manager1");
        managerDao.save(manager);
        client.setManager(manager);
        Client savedClient = clientDao.save(client, manager.getId());

        boolean deleteResult = clientDao.deleteById(savedClient.getId());
        assertTrue(deleteResult);
        Client deletedClient = clientDao.getClientById(savedClient.getId());
        assertNull(deletedClient);
    }

    @Test
    public void testDeleteClient() {
        // Test deleting a client
        Client client = createClientByLoginName("john_doe");
        Manager manager = createManagerByName("Manager1");
        managerDao.save(manager);
        client.setManager(manager);
        Client savedClient = clientDao.save(client, manager.getId());

        boolean deleteResult = clientDao.delete(savedClient);
        assertTrue(deleteResult);
        Client deletedClient = clientDao.getClientById(savedClient.getId());
        assertNull(deletedClient);
    }

    @Test
    public void testGetClients() {
        // Test getting all clients
        Client client1 = createClientByLoginName("client1");
        Client client2 = createClientByLoginName("client2");
        Manager manager = createManagerByName("Manager1");
        managerDao.save(manager);

        client1.setManager(manager);
        client2.setManager(manager);

        clientDao.save(client1, manager.getId());
        clientDao.save(client2, manager.getId());

        List<Client> clients = clientDao.getClients();
        assertEquals(2, clients.size());
    }

    @Test
    public void testGetClientById() {
        // Test getting a client by ID
        Client client = createClientByLoginName("john_doe");
        Manager manager = createManagerByName("Manager1");
        managerDao.save(manager);
        client.setManager(manager);
        Client savedClient = clientDao.save(client, manager.getId());

        Client retrievedClient = clientDao.getClientById(savedClient.getId());
        assertNotNull(retrievedClient);
        assertEquals(savedClient.getId(), retrievedClient.getId());
    }

    @Test
    public void testGetClientByLoginName() {
        // Test getting a client by login name
        Client client = createClientByLoginName("john_doe");
        Manager manager = createManagerByName("Manager1");
        managerDao.save(manager);
        client.setManager(manager);
        clientDao.save(client, manager.getId());

        Client retrievedClient = clientDao.getClientByLoginName("john_doe");
        assertNotNull(retrievedClient);
        assertEquals(client.getId(), retrievedClient.getId());
    }

    @Test
    public void testGetClientsByManagerId() {
        // Test getting clients by manager ID
        Manager manager1 = createManagerByName("Manager1");
        Manager manager2 = createManagerByName("Manager2");
        managerDao.save(manager1);
        managerDao.save(manager2);

        Client client1 = createClientByLoginName("client1");
        Client client2 = createClientByLoginName("client2");
        Client client3 = createClientByLoginName("client3");

        client1.setManager(manager1);
        client2.setManager(manager1);
        client3.setManager(manager2);

        clientDao.save(client1, manager1.getId());
        clientDao.save(client2, manager1.getId());
        clientDao.save(client3, manager2.getId());

        List<Client> clientsManager1 = clientDao.getClientsByManagerId(manager1.getId());
        assertEquals(2, clientsManager1.size());

        List<Client> clientsManager2 = clientDao.getClientsByManagerId(manager2.getId());
        assertEquals(1, clientsManager2.size());
    }

    @Test
    public void testGetClientsWithAssociatedProducts() {
        // Test getting clients with associated products
        Manager manager = createManagerByName("Manager1");
        managerDao.save(manager);

        Client client1 = createClientByLoginName("client1");
        Client client2 = createClientByLoginName("client2");
        client1.setManager(manager);
        client2.setManager(manager);
        clientDao.save(client1, manager.getId());
        clientDao.save(client2, manager.getId());

        Product product1 = createProductByName("Product1");
        Product product2 = createProductByName("Product2");
        client1.addProduct(product1); // Associate products with clients using convenience methods
        client2.addProduct(product2);
        clientDao.update(client1); // Make sure to update the clients after adding products
        clientDao.update(client2);

        List<Client> clientsWithProducts = clientDao.getClientsWithAssociatedProducts();
        assertEquals(2, clientsWithProducts.size());
        assertEquals(1, clientsWithProducts.get(0).getProductList().size());
        assertEquals(1, clientsWithProducts.get(1).getProductList().size());

    }

    private Product createProductByName(String name) {
        Product product = new Product();
        product.setName(name);
        return product;
    }


}

