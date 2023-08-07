package com.project.bankcompany.daoimpl.sproingdatajpa;

import com.project.bankcompany.dao.hibernate.ProductDao;
import com.project.bankcompany.daoimpl.repository.ProductRepository;
import com.project.bankcompany.entity.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("ProductDaoSpringDataJPAImpl")
public class ProductDaoSpringDataJPAImpl implements ProductDao {

    private Logger logger = LoggerFactory.getLogger(ProductDaoSpringDataJPAImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public Product update(Product product) {
        Product updateProduct = productRepository.save(product);
        return updateProduct;
    }

    @Override
    public boolean deleteByName(String productName) {
        try {
            productRepository.deleteByName(productName);
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete product by name={}, error={}", productName, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(Long productId) {
        try {
            productRepository.deleteById(productId);
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete product by id={}, error={}", productId, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        try {
            productRepository.delete(product);
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete product, error={}", e.getMessage());
            return false;
        }
    }

    @Override
    public List<Product> getProducts() {
        List<Product> productList = productRepository.findAll();
        if (productList == null)
            productList = new ArrayList<>();
        return productList;
    }

    @Override
    public Product getProductById(Long id) {
        Product product = null;
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent())
            product = productOptional.get();
        return product;
    }

    @Override
    public Product getProductByName(String productName) {
        Product product = productRepository.findByName(productName);
        return product;
    }

    @Override
    public List<Product> getProductsWithAssociatedClients() {
        return productRepository.findAllByClientListIsNotNull();
    }

    @Override
    public Product getProductsWithAssociatedClientsById(Long productId) {
        return productRepository.findByIdAndClientListIsNotNull(productId);
    }

    @Override
    public Product getProductsWithAssociatedClientsByName(String productName) {
        return productRepository.findByNameAndClientListIsNotNull(productName);
    }
}
