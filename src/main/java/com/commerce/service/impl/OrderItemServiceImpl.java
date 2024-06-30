package com.commerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.Exception.ResourceNotFoundException;
import com.commerce.model.OrderItem;
import com.commerce.model.Product;
import com.commerce.repository.OrderItemRepository;
import com.commerce.repository.ProductRepository;
import com.commerce.service.OrderItemService;

@Service
@Transactional
public class OrderItemServiceImpl implements OrderItemService {
	@Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductRepository productRepository;

    

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found for this id :: " + id));
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Transactional
    public OrderItem updateOrderItem(Long orderItemId, Long productId, Integer quantity) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        return orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = getOrderItemById(id);
        orderItemRepository.delete(orderItem);
    }
}