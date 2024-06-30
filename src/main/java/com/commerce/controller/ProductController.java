package com.commerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.commerce.model.Product;
import com.commerce.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	@Operation(summary = "Get all products", tags = { "Product-Controller" }, responses = {
			@ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))) })
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a product by ID", tags = { "Product-Controller" }, responses = {
			@ApiResponse(responseCode = "200", description = "Successful Operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
			@ApiResponse(responseCode = "404", description = "Product not found") })
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Product product = productService.getProductById(id);
		if (product != null) {
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@Operation(summary = "Create a new product", tags = { "Product-Controller" }, responses = {
			@ApiResponse(responseCode = "201", description = "Product created successfully", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class)) }) })
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product createdProduct = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update an existing product", tags = { "Product-Controller" }, responses = {
			@ApiResponse(responseCode = "200", description = "Product updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
			@ApiResponse(responseCode = "404", description = "Product not found") })
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
		Product updatedProduct = productService.updateProduct(id, productDetails);
		if (updatedProduct != null) {
			return ResponseEntity.ok(updatedProduct);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a product by ID", tags = { "Product-Controller" }, responses = {
			@ApiResponse(responseCode = "204", description = "Product deleted", content = @Content),
			@ApiResponse(responseCode = "404", description = "Product not found") })
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
}
