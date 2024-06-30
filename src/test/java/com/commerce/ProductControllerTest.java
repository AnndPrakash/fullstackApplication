package com.commerce;

import com.commerce.controller.ProductController;
import com.commerce.model.Product;
import com.commerce.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController(productService);
    }

    @Test
    public void getAllProductsTest() {
        // Given
        Product product1 = new Product(1L, "Product 1", 100.00);
        Product product2 = new Product(2L, "Product 2", 150.00);
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        // When
        ResponseEntity<List<Product>> response = productController.getAllProducts();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void getProductByIdTest() {
        // Given
        Product product = new Product(1L, "Product 1", 100.00);
        when(productService.getProductById(1L)).thenReturn(product);

        // When
        ResponseEntity<Product> response = productController.getProductById(1L);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Product 1", response.getBody().getName());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void createProductTest() {
        // Given
        Product product = new Product(null, "New Product", 200.00);
        Product savedProduct = new Product(1L, "New Product", 200.00);
        when(productService.createProduct(any(Product.class))).thenReturn(savedProduct);

        // When
        ResponseEntity<Product> response = productController.createProduct(product);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("New Product", response.getBody().getName());
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    public void updateProductTest() {
        // Given
        Product existingProduct = new Product(1L, "Existing Product", 250.00);
        Product updatedDetails = new Product(1L, "Updated Product", 300.00);
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(updatedDetails);

        // When
        ResponseEntity<Product> response = productController.updateProduct(1L, existingProduct);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Product", response.getBody().getName());
        assertEquals(300.00, response.getBody().getPrice());
        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    public void deleteProductTest() {
        // Given
        doNothing().when(productService).deleteProduct(1L);

        // When
        ResponseEntity<Void> response = productController.deleteProduct(1L);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService, times(1)).deleteProduct(1L);
    }

  
}
