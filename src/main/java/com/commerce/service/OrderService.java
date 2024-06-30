package com.commerce.service;

import java.util.List;
import java.util.Optional;

import com.commerce.model.Order;

public interface OrderService {
    Order saveOrder(Order order);
    Optional<Order> getOrderById(Long id);
    List<Order> getAllOrders();
    Order updateOrder(Long id, Order order);
    void deleteOrder(Long id);
}