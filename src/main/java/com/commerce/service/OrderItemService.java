package com.commerce.service;

import java.util.List;

import com.commerce.model.OrderItem;

public interface OrderItemService {
    OrderItem saveOrderItem(OrderItem orderItem);
    OrderItem getOrderItemById(Long id);
    List<OrderItem> getAllOrderItems();
    OrderItem updateOrderItem(Long orderItemId, Long productId, Integer quantity);
    void deleteOrderItem(Long id);
}