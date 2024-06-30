package com.commerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.Exception.ResourceNotFoundException;
import com.commerce.model.Order;
import com.commerce.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Get all orders", tags = { "Order-Controller" })
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Get order by ID", tags = { "Order-Controller" })
    @ApiResponse(responseCode = "200", description = "Found the order", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)))
    @ApiResponse(responseCode = "404", description = "Order not found")
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "Create a new order", tags = { "Order-Controller" })
    @ApiResponse(responseCode = "201", description = "Order created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)))
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order newOrder = orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    @Operation(summary = "Update an existing order", tags = { "Order-Controller" })
    @ApiResponse(responseCode = "200", description = "Order updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class)))
    @ApiResponse(responseCode = "404", description = "Order not found")
    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Order existingOrder = orderService.getOrderById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
        
        // Update the existing order with orderDetails
        existingOrder.setStatus(orderDetails.getStatus());
        existingOrder.setOrderDate(orderDetails.getOrderDate());
        existingOrder.setPrice(orderDetails.getPrice());
        // Add more fields to be updated as needed

        Order updatedOrder = orderService.saveOrder(existingOrder);
        return ResponseEntity.ok(updatedOrder);
    }

    @Operation(summary = "Delete an order by ID", tags = { "Order-Controller" })
    @ApiResponse(responseCode = "204", description = "Order deleted")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Order order = orderService.getOrderById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
