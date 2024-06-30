package com.commerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.commerce.controller.OrderController;
import com.commerce.model.Order;
import com.commerce.service.OrderService;

public class OrderControllerTest {

    private OrderController orderController;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        // Mock the OrderService
        orderService = mock(OrderService.class);
        // Inject the mocked OrderService into the OrderController
        orderController = new OrderController(orderService);
    }

    @Test
    void testGetOrderById_OrderFound() {
        // Given
        Long orderId = 1L;
        Order mockOrder = new Order(orderId, new Date(), "PROCESSING", 100.0);
        when(orderService.getOrderById(anyLong())).thenReturn(Optional.of(mockOrder));

        // When
        ResponseEntity<Order> response = orderController.getOrderById(orderId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Expected status code to be OK");
        assertNotNull(response.getBody(), "Expected body to not be null");
        assertEquals(orderId, response.getBody().getId(), "Expected order ID to match");
    }

    @Test
    @Disabled
    void testGetOrderById_OrderNotFound() {
        // Given
        Long orderId = 1L;
        when(orderService.getOrderById(anyLong())).thenReturn(Optional.empty());

        // When
        ResponseEntity<Order> response = orderController.getOrderById(orderId);

        // Then Use This
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }




}
