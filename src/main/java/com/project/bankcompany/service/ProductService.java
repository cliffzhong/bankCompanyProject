package com.project.bankcompany.service;



import com.project.bankcompany.dto.ProductDto;
import com.project.bankcompany.entity.Product;

import java.util.List;

public interface ProductService {

    ProductDto save(ProductDto productDto);

    ProductDto update(ProductDto productDto);

    boolean deleteByName(String productDtoName);

    boolean deleteById(Long productDtoId);

    boolean delete(ProductDto productDto);

    List<ProductDto> getProducts();

    ProductDto getProductById(Long id);

    ProductDto getProductByName(String productDtoName);

    List<ProductDto> getProductsWithAssociatedClients();

    ProductDto getProductsWithAssociatedClientsById(Long productDtoId);

    ProductDto getProductsWithAssociatedClientsByName(String productDtoName);
}
