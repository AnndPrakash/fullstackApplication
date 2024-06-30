package com.commerce.service;

import java.util.List;

import com.commerce.model.Product;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Long id, Product productDetails);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    void deleteProduct(Long id);
}