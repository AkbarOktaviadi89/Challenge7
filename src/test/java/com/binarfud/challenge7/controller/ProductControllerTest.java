package com.binarfud.challenge7.controller;

import com.binarfud.challenge7.Controller.ProductController;
import com.binarfud.challenge7.Model.Product;
import com.binarfud.challenge7.Service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllProduct() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());

        when(productService.findAllProduct()).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getAllProduct();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetAllProductEmpty() {
        List<Product> products = new ArrayList<>();

        when(productService.findAllProduct()).thenReturn(products);

        ResponseEntity<List<Product>> response = productController.getAllProduct();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetProductById() {
        String productId = "1";
        Product product = new Product();
        when(productService.findById(productId)).thenReturn(Optional.of(product));

        ResponseEntity<Optional<Product>> response = productController.getProductById(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetProductByIdNotFound() {
        String productId = "1";
        when(productService.findById(productId)).thenReturn(Optional.empty());

        ResponseEntity<Optional<Product>> response = productController.getProductById(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        when(productService.addNewProduct(product)).thenReturn(true);

        ResponseEntity response = productController.createProduct(product);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreateProductError() {
        Product product = new Product();
        when(productService.addNewProduct(product)).thenReturn(false);

        ResponseEntity response = productController.createProduct(product);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateProduct() {
        String productId = "1";
        Product product = new Product();
        when(productService.findById(productId)).thenReturn(Optional.of(product));
//        when(productService.updateById(product)).thenReturn(product);

        ResponseEntity<Product> response = productController.updateProduct(productId, product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testUpdateProductNotFound() {
        String productId = "1";
        when(productService.findById(productId)).thenReturn(Optional.empty());

        ResponseEntity<Product> response = productController.updateProduct(productId, new Product());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteProduct() {
        String productId = "1";
        when(productService.findById(productId)).thenReturn(Optional.of(new Product()));

        ResponseEntity<HttpStatus> response = productController.deleteProduct(productId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteProductNotFound() {
        String productId = "1";
        when(productService.findById(productId)).thenReturn(Optional.empty());

        ResponseEntity<HttpStatus> response = productController.deleteProduct(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteAllProduct() {
        ResponseEntity<HttpStatus> response = productController.deleteAllProduct();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
