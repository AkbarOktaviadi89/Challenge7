package com.binarfud.challenge7.Service.Impl;

import com.binarfud.challenge7.Model.Product;
import com.binarfud.challenge7.Repository.ProductRepository;
import com.binarfud.challenge7.Service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAllProduct() {
        try {
            log.info("Retrieving all available products.");
            return productRepository.findAll();
        } catch (Exception e) {
            log.error("Error while retrieving all products: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve products.", e);
        }
    }

    @Override
    public Optional<Product> findById(String id) {
        try {
            log.info("Retrieving product by ID: {}", id);
            return productRepository.findById(id);
        } catch (Exception e) {
            log.error("Error while retrieving product by ID: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve product by ID.", e);
        }
    }

    @Override
    public Boolean addNewProduct(Product product) {
        try {
            log.info("Adding a new product.");
            Product newProduct = productRepository.save(product);
            if (newProduct != null) {
                return true;
            } else {
                log.error("Failed adding a new Product");
                return false;
            }
        } catch (Exception e) {
            log.error("Error while adding a new product: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add a new product.", e);
        }
    }

    @Override
    public void updateById(Product product) {
        String productCode = product.getProductCode();
        if (productCode != null) {
            try {
                log.info("Updating product with code {}.", productCode);
                Optional<Product> existingProduct = productRepository.findById(productCode);
                if (existingProduct.isPresent()) {
                    productRepository.save(product);
                } else {
                    log.error("Product with code {} not found. Update failed.", productCode);
                }
            } catch (Exception e) {
                log.error("Error while updating product: {}", e.getMessage(), e);
            }
        } else {
            log.error("Product code is null. Update failed.");
            throw new IllegalArgumentException("Product code cannot be null.");
        }
    }




    @Override
    public void deleteProductbyId(String productCode) {
        try {
            log.info("Deleting product with code {}.", productCode);
            productRepository.deleteById(productCode);
        } catch (Exception e) {
            log.error("Error while deleting product: {}", e.getMessage(), e);
        }
    }

    @Override
    public void deleteAllProduct() {
        try {
            log.info("Deleting all products.");
            productRepository.deleteAll();
        } catch (Exception e) {
            log.error("Error while deleting all products: {}", e.getMessage(), e);
        }
    }
}

