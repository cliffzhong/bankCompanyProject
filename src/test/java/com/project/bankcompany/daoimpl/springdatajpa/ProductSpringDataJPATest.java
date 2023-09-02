package com.project.bankcompany.daoimpl.springdatajpa;

import com.project.bankcompany.dao.ClientDao;
import com.project.bankcompany.dao.ManagerDao;
import com.project.bankcompany.dao.ProductDao;
import com.project.bankcompany.entity.Client;
import com.project.bankcompany.entity.Manager;
import com.project.bankcompany.entity.Product;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductSpringDataJPATest {

    @Autowired
    @Qualifier("ProductDaoSpringDataJPAImpl")
    private ProductDao productDao;

    @Autowired
    @Qualifier("ClientDaoSpringDataJPAImpl")
    private ClientDao clientDao;

    @Autowired
    @Qualifier("ManagerDaoSpringDataJPAImpl")
    private ManagerDao managerDao;

    @BeforeEach
    public void setup() {
        // Create and save a manager
        Manager manager = createManagerByName("John");
        managerDao.save(manager);

        // Create and save clients
        Client client1 = createClientByLoginName("Alice");
        client1.setManager(manager);
        clientDao.save(client1, manager.getId());

        Client client2 = createClientByLoginName("Bob");
        client2.setManager(manager);
        clientDao.save(client2, manager.getId());
    }

    @AfterEach
    public void cleanup() {
        // Delete all products, clients, and manager from the database after each test
        productDao.getProducts().forEach(product -> productDao.delete(product));
        clientDao.getClients().forEach(client -> clientDao.delete(client));
        managerDao.getManagers().forEach(manager -> managerDao.delete(manager));
    }

    @Test
    public void testSaveProduct() {
        // Create a product
        Product product = new Product();
        product.setName("Product1");
        product.setDescription("Product 1 description");
        product.setCreateDate(LocalDate.now());

        // Save the product
        Product savedProduct = productDao.save(product);

        // Check if the product was saved successfully
        assertNotNull(savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(product.getDescription(), savedProduct.getDescription());
        assertEquals(product.getCreateDate(), savedProduct.getCreateDate());
    }

    @Test
    public void testUpdateProduct() {
        // Create a product and save it
        Product product = createProductByName("Product1");
        Product savedProduct = productDao.save(product);

        // Update the product name and description
        savedProduct.setName("UpdatedProduct");
        savedProduct.setDescription("Updated product description");
        Product updatedProduct = productDao.update(savedProduct);

        // Check if the product was updated successfully
        assertEquals(savedProduct.getId(), updatedProduct.getId());
        assertEquals("UpdatedProduct", updatedProduct.getName());
        assertEquals("Updated product description", updatedProduct.getDescription());
    }

    @Test
    public void testDeleteProductByName() {
        // Create and save a product
        Product product = createProductByName("Product1");
        productDao.save(product);

        // Delete the product by name
        boolean isDeleted = productDao.deleteByName("Product1");

        // Check if the product was deleted successfully
        assertTrue(isDeleted);
        assertNull(productDao.getProductByName("Product1"));
    }

    @Test
    public void testDeleteProductById() {
        // Create and save a product
        Product product = createProductByName("Product1");
        Product savedProduct = productDao.save(product);

        // Delete the product by id
        boolean isDeleted = productDao.deleteById(savedProduct.getId());

        // Check if the product was deleted successfully
        assertTrue(isDeleted);
        assertNull(productDao.getProductById(savedProduct.getId()));
    }

    @Test
    public void testGetProducts() {
        // Create and save two products
        Product product1 = createProductByName("Product1");
        Product product2 = createProductByName("Product2");
        productDao.save(product1);
        productDao.save(product2);

        // Get all products
        List<Product> products = productDao.getProducts();

        // Check if all products are retrieved
        assertEquals(2, products.size());
        assertTrue(products.contains(product1));
        assertTrue(products.contains(product2));
    }

    @Test
    public void testGetProductById() {
        // Create and save a product
        Product product = createProductByName("Product1");
        Product savedProduct = productDao.save(product);

        // Get the product by id
        Product retrievedProduct = productDao.getProductById(savedProduct.getId());

        // Check if the correct product is retrieved
        assertEquals(savedProduct, retrievedProduct);
    }

    @Test
    public void testGetProductByName() {
        // Create and save a product
        Product product = createProductByName("Product1");
        productDao.save(product);

        // Get the product by name
        Product retrievedProduct = productDao.getProductByName("Product1");

        // Check if the correct product is retrieved
        assertEquals(product, retrievedProduct);
    }

    @Test
    public void testGetProductsWithAssociatedClients() {

    }


    @Test
    public void testGetProductsWithAssociatedClientsById() {

    }

    @Test
    public void testGetProductsWithAssociatedClientsByName() {

    }

    private Manager createManagerByName(String name) {
        Manager manager = new Manager();
        manager.setName(name);
        manager.setDescription(name + " Manager");
        return manager;
    }

    private Client createClientByLoginName(String loginName) {
        Client client = new Client();
        client.setLoginName(loginName);
        client.setEmail(loginName + " @abc.com");
        return client;
    }

    private Product createProductByName(String name) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(name + " Product");
        product.setCreateDate(LocalDate.now());
        return product;
    }
}
