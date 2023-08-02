package com.project.bankcompany.daoimpl.hibernate;

import com.project.bankcompany.entity.Client;
import com.project.bankcompany.entity.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


public class ProductDaoHibernateTest {

    private static ProductDaoHibernateImpl productDao;
    private Logger logger = LoggerFactory.getLogger(ProductDaoHibernateTest.class);

    @BeforeAll
    public static void initAll() {
        productDao = new ProductDaoHibernateImpl();
    }

    @AfterAll
    public static void teardownAll() {
        productDao = null;
    }

    @BeforeEach
    public void setupEach() {
    }

    @AfterEach
    public void cleanupEach() {
    }

    @Test
    public void testSaveProduct() {
        Product product = createProduct("Test Product", "Test Description", LocalDate.now());
        Product savedProduct = productDao.save(product);
        assertNotNull(savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getDescription(), savedProduct.getDescription());
        assertEquals(product.getCreateDate(), savedProduct.getCreateDate());
    }

    private Product createProduct(String name, String description, LocalDate createDate) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCreateDate(createDate);
        return product;
    }

    @Test
    public void testUpdateProduct() {
        // Create and save a product
        Product product = createProduct("Product for Update", "Description", LocalDate.now());
        productDao.save(product);

        // Update the product
        String updatedDescription = "Updated Description";
        product.setDescription(updatedDescription);
        Product updatedProduct = productDao.update(product);

        assertEquals(updatedDescription, updatedProduct.getDescription());
    }

    @Test
    public void testDeleteProduct() {
        // Create and save a product
        Product product = productDao.getProductById(11L);

        // Delete the product
        assertTrue(productDao.delete(product));

        // Verify the product is deleted
        Product deletedProduct = productDao.getProductById(product.getId());
        assertNull(deletedProduct);
    }

    @Test
    public void testGetProducts() {
        // Create and save a few products
        Product product1 = createProduct("Product 1", "Description 1", LocalDate.now());
        productDao.save(product1);

        Product product2 = createProduct("Product 2", "Description 2", LocalDate.now());
        productDao.save(product2);

        Product product3 = createProduct("Product 3", "Description 3", LocalDate.now());
        productDao.save(product3);

        // Get all products
        List<Product> productList = productDao.getProducts();

        // Verify the number of products
        assertEquals(3, productList.size());

        // Verify the names of products
        List<String> productNames = productList.stream().map(Product::getName).collect(Collectors.toList());
        assertTrue(productNames.contains("Product 1"));
        assertTrue(productNames.contains("Product 2"));
        assertTrue(productNames.contains("Product 3"));
    }

    @Test
    public void testGetProductById() {
        // Create and save a product
        Product product = createProduct("Product for Get By Id", "Description", LocalDate.now());
        productDao.save(product);

        // Get the product by ID
        Product retrievedProduct = productDao.getProductById(product.getId());

        // Verify the retrieved product
        assertNotNull(retrievedProduct);
        assertEquals(product.getName(), retrievedProduct.getName());
        assertEquals(product.getDescription(), retrievedProduct.getDescription());
        assertEquals(product.getCreateDate(), retrievedProduct.getCreateDate());
    }

    @Test
    public void testGetProductByName() {
        // Create and save a product
        Product product = createProduct("Product for Get By Name", "Description", LocalDate.now());
        productDao.save(product);

        // Get the product by name
        Product retrievedProduct = productDao.getProductByName("Product for Get By Name");

        // Verify the retrieved product
        assertNotNull(retrievedProduct);
        assertEquals(product.getId(), retrievedProduct.getId());
        assertEquals(product.getDescription(), retrievedProduct.getDescription());
        assertEquals(product.getCreateDate(), retrievedProduct.getCreateDate());
    }

    @Test
    public void testGetProductsWithAssociatedClients() {
        // Create and save a product with associated clients
        Product product = createProduct("Product with Clients", "Description", LocalDate.now());
        Client client1 = createClient("Client 1", "Password1", "Client", "One", "client1@example.com", LocalDate.now());
        Client client2 = createClient("Client 2", "Password2", "Client", "Two", "client2@example.com", LocalDate.now());
        product.addClient(client1);
        product.addClient(client2);
        productDao.save(product);

        // Get products with associated clients
        List<Product> productsWithClients = productDao.getProductsWithAssociatedClients();

        // Verify the number of products
        assertEquals(1, productsWithClients.size());

        // Verify the number of clients associated with the product
        List<Client> clients = productsWithClients.get(0).getClientList();
        assertEquals(2, clients.size());

        // Verify the names of clients
        List<String> clientNames = clients.stream().map(Client::getFirstName).collect(Collectors.toList());
        assertTrue(clientNames.contains("Client 1"));
        assertTrue(clientNames.contains("Client 2"));
    }

    @Test
    public void testGetProductsWithAssociatedClientsById() {
        // Create and save a product with associated clients
        Product product = createProduct("Product with Clients", "Description", LocalDate.now());
        Client client1 = createClient("Client 1", "Password1", "Client", "One", "client1@example.com", LocalDate.now());
        Client client2 = createClient("Client 2", "Password2", "Client", "Two", "client2@example.com", LocalDate.now());
        product.addClient(client1);
        product.addClient(client2);
        productDao.save(product);

        // Get product with associated clients by ID
        Product retrievedProduct = productDao.getProductsWithAssociatedClientsById(product.getId());

        // Verify the retrieved product
        assertNotNull(retrievedProduct);
        assertEquals(product.getName(), retrievedProduct.getName());
        assertEquals(product.getDescription(), retrievedProduct.getDescription());
        assertEquals(product.getCreateDate(), retrievedProduct.getCreateDate());

        // Verify the number of clients associated with the product
        List<Client> clients = retrievedProduct.getClientList();
        assertEquals(2, clients.size());

        // Verify the names of clients
        List<String> clientNames = clients.stream().map(Client::getFirstName).collect(Collectors.toList());
        assertTrue(clientNames.contains("Client 1"));
        assertTrue(clientNames.contains("Client 2"));
    }

    @Test
    public void testGetProductsWithAssociatedClientsByName() {
        // Create and save a product with associated clients
        Product product = createProduct("Product with Clients", "Description", LocalDate.now());
        Client client1 = createClient("Client 1", "Password1", "Client", "One", "client1@example.com", LocalDate.now());
        Client client2 = createClient("Client 2", "Password2", "Client", "Two", "client2@example.com", LocalDate.now());
        product.addClient(client1);
        product.addClient(client2);
        productDao.save(product);

        // Get product with associated clients by name
        Product retrievedProduct = productDao.getProductsWithAssociatedClientsByName("Product with Clients");

        // Verify the retrieved product
        assertNotNull(retrievedProduct);
        assertEquals(product.getId(), retrievedProduct.getId());
        assertEquals(product.getDescription(), retrievedProduct.getDescription());
        assertEquals(product.getCreateDate(), retrievedProduct.getCreateDate());

        // Verify the number of clients associated with the product
        List<Client> clients = retrievedProduct.getClientList();
        assertEquals(2, clients.size());

        // Verify the names of clients
        List<String> clientNames = clients.stream().map(Client::getFirstName).collect(Collectors.toList());
        assertTrue(clientNames.contains("Client 1"));
        assertTrue(clientNames.contains("Client 2"));
    }

    // Add more test methods for other ProductDao methods if required.

    private Client createClient(String loginName, String password, String firstName, String lastName, String email, LocalDate enrolledDate) {
        Client client = new Client();
        client.setLoginName(loginName);
        client.setPassword(password);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setEmail(email);
        client.setEnrolledDate(enrolledDate);
        return client;
    }
}