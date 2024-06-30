package com.commerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.model.Order;
import com.commerce.repository.OrderRepository;
import com.commerce.service.OrderService;
import com.commerce.Exception.ResourceNotFoundException;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, Order orderDetails) {
        Order order = getOrderById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
        
        // Assuming you want to update fields like status, price, etc.
        // Example update logic (you should adjust according to your actual order fields)
        order.setOrderDate(orderDetails.getOrderDate());
        order.setStatus(orderDetails.getStatus());
        order.setPrice(orderDetails.getPrice());
        // add more fields to update as needed
        
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = getOrderById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
        orderRepository.delete(order);
    }
}
