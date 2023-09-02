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
public class ProductServiceImpl implements ProductService {

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
    public ProductDto save(ProductDto productDto) {
        Product product = productDto.convertProductDtoToProduct();
        Product savedProduct = productDao.save(product);
        ProductDto savedProductDto = savedProduct.convertProductToProductDto();
        return savedProductDto;
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        Product product = productDto.convertProductDtoToProduct();
        Product updatedProduct = productDao.update(product);
        ProductDto updatedProductDto = updatedProduct.convertProductToProductDto();
        return updatedProductDto;
    }

    @Override
    public boolean deleteByName(String productDtoName) {
        Product product = productDao.getProductByName(productDtoName);
        if (product != null) {
            return productDao.delete(product);
        }
        return false;
    }

    @Override
    public boolean deleteById(Long productDtoId) {
        Product product = productDao.getProductById(productDtoId);
        if (product != null) {
            return productDao.delete(product);
        }
        return false;
    }

    @Override
    public boolean delete(ProductDto productDto) {
        Product product = productDao.getProductById(productDto.getId());
        if (product != null) {
            return productDao.delete(product);
        }
        return false;
    }

    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = productDao.getProducts();
        return getProductDtoListFromProductList(products);
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productDao.getProductById(id);
        return product != null ? product.convertProductToProductDto() : null;
    }

    @Override
    public ProductDto getProductByName(String productDtoName) {
        Product product = productDao.getProductByName(productDtoName);
        return product != null ? product.convertProductToProductDto() : null;
    }

    @Override
    public List<ProductDto> getProductsWithAssociatedClients() {
        List<Product> products = productDao.getProductsWithAssociatedClients();
        return getProductDtoListFromProductList(products);
    }

    @Override
    public ProductDto getProductsWithAssociatedClientsById(Long productDtoId) {
        Product product = productDao.getProductsWithAssociatedClientsById(productDtoId);
        return product != null ? product.convertProductToProductDto() : null;
    }

    @Override
    public ProductDto getProductsWithAssociatedClientsByName(String productDtoName) {
        Product product = productDao.getProductsWithAssociatedClientsByName(productDtoName);
        return product != null ? product.convertProductToProductDto() : null;
    }

    private List<ProductDto> getProductDtoListFromProductList(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            productDtoList.add(product.convertProductToProductDto());
        }
        return productDtoList;
    }
}
