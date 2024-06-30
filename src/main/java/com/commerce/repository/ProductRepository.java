package com.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}