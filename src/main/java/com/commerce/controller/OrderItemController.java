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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.model.OrderItem;
import com.commerce.service.OrderItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    @Operation(summary = "Get a list of all order items", tags = {"Order-Items-Controller"})
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order item by ID",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Found the order item",
                                content = @Content(mediaType = "application/json", 
                                schema = @Schema(implementation = OrderItem.class))),
                   @ApiResponse(responseCode = "404", description = "Order item not found")
               }, tags = {"Order-Items-Controller"})
    public ResponseEntity<OrderItem> getOrderItemById(@Parameter(description = "ID of the order item to be obtained")
                                                      @PathVariable Long id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(orderItem);
    }

    @PostMapping
    @Operation(summary = "Create a new order item",
               responses = @ApiResponse(responseCode = "201", description = "Order item created",
                                        content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = OrderItem.class))), tags = {"Order-Items-Controller"})
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
        OrderItem newOrderItem = orderItemService.saveOrderItem(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrderItem);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing order item",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Order item updated",
                                content = @Content(mediaType = "application/json", 
                                schema = @Schema(implementation = OrderItem.class))),
                   @ApiResponse(responseCode = "404", description = "Order item not found")
               }, tags = {"Order-Items-Controller"})
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestParam Long productId, @RequestParam Integer quantity) {
        OrderItem updatedOrderItem = orderItemService.updateOrderItem(id, productId, quantity);
        return ResponseEntity.ok(updatedOrderItem);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order item by ID",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Order item deleted"),
                   @ApiResponse(responseCode = "404", description = "Order item not found")
               }, tags = {"Order-Items-Controller"})
    public ResponseEntity<Void> deleteOrderItem(@Parameter(description = "ID of the order item to be deleted")
                                                @PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok().build();
    }
}
