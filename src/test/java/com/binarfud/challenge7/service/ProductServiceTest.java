package com.binarfud.challenge7.service;

import com.binarfud.challenge7.Model.Product;
import com.binarfud.challenge7.Repository.ProductRepository;
import com.binarfud.challenge7.Service.Impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAllProduct() {
        List<Product> products = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.findAllProduct();

        assertEquals(products, result);
        verify(productRepository).findAll();
    }

    @Test
    public void testFindProductById() {
        String productId = "123";
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findById(productId);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository).findById(productId);
    }

    @Test
    public void testAddNewProduct() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        boolean result = productService.addNewProduct(product);

        assertTrue(result);
        verify(productRepository).save(product);
    }

    @Test
    public void testAddNewProductFail() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(null);

        boolean result = productService.addNewProduct(product);

        assertFalse(result);
        verify(productRepository).save(product);
    }

    @Test
    public void testUpdateProduct() {
        String productCode = "123";

        Product product = new Product();
        product.setProductCode(productCode);

        when(productRepository.findById(productCode)).thenReturn(Optional.of(product));

        productService.updateById(product);

        verify(productRepository).findById(productCode);
        verify(productRepository).save(product);
    }


    @Test
    public void testUpdateProductNotFound() {
        String productCode = "123";
        when(productRepository.findById(productCode)).thenReturn(Optional.empty());

        productService.updateById(new Product());

        verify(productRepository).findById(productCode);
        verify(productRepository, never()).save(any(Product.class));
    }


    @Test
    public void testDeleteProductById() {
        String productCode = "123";
        doNothing().when(productRepository).deleteById(productCode);

        productService.deleteProductbyId(productCode);

        verify(productRepository).deleteById(productCode);
    }

    @Test
    public void testDeleteAllProducts() {
        doNothing().when(productRepository).deleteAll();

        productService.deleteAllProduct();

        verify(productRepository).deleteAll();
    }
}
