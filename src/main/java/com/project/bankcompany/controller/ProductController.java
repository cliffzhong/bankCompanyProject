package com.project.bankcompany.controller;

import com.project.bankcompany.dto.ProductDto;
import com.project.bankcompany.exception.ExceptionResponse;
import com.project.bankcompany.exception.ItemNotFoundException;
import com.project.bankcompany.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/project_bank")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> findAllProducts() {
        List<ProductDto> productDtoList = productServiceImpl.getProducts();
        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> findProductById(@PathVariable("id") Long productId) {
        try {
            ProductDto productDto = productServiceImpl.getProductById(productId);
            return ResponseEntity.ok(productDto);
        } catch (ItemNotFoundException e) {
            ExceptionResponse exceptionResponse = new ExceptionResponse("Product not found", LocalDateTime.now(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto) {
        // Validate productDto, perform any necessary processing, and then save
        ProductDto savedProductDto = productServiceImpl.save(productDto);
        return ResponseEntity.created(URI.create("/project_bank/products/" + savedProductDto.getId())).body(savedProductDto);
    }

    @PutMapping("/products")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        // Validate productDto, perform any necessary processing, and then update
        ProductDto updatedProductDto = productServiceImpl.update(productDto);
        return ResponseEntity.ok(updatedProductDto);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long productId) {
        // Delete the product by ID
        boolean deleteResult = productServiceImpl.deleteById(productId);
        if (deleteResult) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
}

