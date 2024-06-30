package com.commerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import com.commerce.controller.OrderItemController;
import com.commerce.model.OrderItem;
import com.commerce.service.OrderItemService;

public class OrderItemControllerTest {

    @Mock
    private OrderItemService orderItemService;

    @InjectMocks
    private OrderItemController orderItemController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllOrderItemsTest() {
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(1L);
        orderItem1.setQuantity(5);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(2L);
        orderItem2.setQuantity(3);
        when(orderItemService.getAllOrderItems()).thenReturn(List.of(orderItem1, orderItem2));

        ResponseEntity<List<OrderItem>> response = orderItemController.getAllOrderItems();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void getOrderItemByIdTest() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(10);
        when(orderItemService.getOrderItemById(1L)).thenReturn(orderItem);

        ResponseEntity<OrderItem> response = orderItemController.getOrderItemById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void createOrderItemTest() {
        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setQuantity(10);

        OrderItem savedOrderItem = new OrderItem();
        savedOrderItem.setId(1L);
        savedOrderItem.setQuantity(10);
        when(orderItemService.saveOrderItem(newOrderItem)).thenReturn(savedOrderItem);

        ResponseEntity<OrderItem> response = orderItemController.createOrderItem(newOrderItem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    public void updateOrderItemTest() {
        OrderItem updatedOrderItem = new OrderItem();
        updatedOrderItem.setId(1L);
        updatedOrderItem.setQuantity(10);
        Long productId = 2L; // Assuming you have a productId to pass
        Integer quantity = 10; // Assuming you want to update the quantity
        when(orderItemService.updateOrderItem(1L, productId, quantity)).thenReturn(updatedOrderItem);

        ResponseEntity<OrderItem> response = orderItemController.updateOrderItem(1L, productId, quantity);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10, response.getBody().getQuantity());
    }
}
