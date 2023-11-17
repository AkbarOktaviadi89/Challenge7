package com.binarfud.challenge7.Controller;

import com.binarfud.challenge7.Model.Product;
import com.binarfud.challenge7.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Transactional
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Operation(summary = "Get All Products Available")
    @GetMapping("/get-products")
    public ResponseEntity<List<Product>> getAllProduct() {
        try {
            List<Product> products = productService.findAllProduct();
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(products, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Products By Id")
    @GetMapping("/get-products/{id}")
    public ResponseEntity<Optional<Product>> getProductById(@PathVariable("id") String id) {
        try {
            Optional<Product> productData = productService.findById(id);
            if (productData.isPresent()) {
                return new ResponseEntity<>(productData, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured("ROLE_MERCHANT")
    @Operation(summary = "Create New Products")
    @PostMapping("/create-products")
    public ResponseEntity createProduct(@RequestBody Product product) {
        try {
            Product _product = new Product(product.getProductName(), product.getPrice(), product.getMerchant());
            if (productService.addNewProduct(_product)) {
                return new ResponseEntity<>(_product, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>("Gagal Menambahkan Product", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("Error creating product: {}", e);
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Secured("ROLE_MERCHANT")
    @Operation(summary = "Edit Products By Id")
    @PutMapping("/edit-products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        Optional<Product> productData = productService.findById(id);
        try {
            if (productData.isPresent()) {
                Product _product = productData.get();
                _product.setProductName(product.getProductName());
                _product.setPrice(product.getPrice());
                _product.setMerchant(product.getMerchant());
                productService.updateById(_product);
                return new ResponseEntity<>(_product, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Secured("ROLE_MERCHANT")
    @Operation(summary = "Delete Products By Id")
    @DeleteMapping("/delete-products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") String id) {
        try {
            Optional <Product> productData = productService.findById(id);
            if(productData.isPresent()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Secured("ROLE_MERCHANT")
    @Operation(summary = "Delete All Products")
    @DeleteMapping("/delete-products")
    public ResponseEntity<HttpStatus> deleteAllProduct() {
        try {
            productService.deleteAllProduct();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
