package com.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.commerce.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
