package com.binarfud.challenge7.Controller;

import com.binarfud.challenge7.Enum.MerchantStatus;
import com.binarfud.challenge7.Model.Merchant;
import com.binarfud.challenge7.Model.Orders;
import com.binarfud.challenge7.Model.Product;
import com.binarfud.challenge7.Model.Users;
import com.binarfud.challenge7.Service.MerchantService;
import com.binarfud.challenge7.Service.OrdersService;
import com.binarfud.challenge7.Service.ProductService;
import com.binarfud.challenge7.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Transactional
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/async")
public class AsyncController {
    @Autowired
    MerchantService merchantService;

    @Autowired
    OrdersService ordersService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Transactional(readOnly = true)
    @Operation(summary = "Get All Merchants Available with Async")
    @GetMapping(value = "/get-merchants")
    public CompletableFuture<ResponseEntity<List<Merchant>>> getMerchantByStatusAsync(
            @RequestParam(required = false) MerchantStatus merchantStatus) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<Merchant> merchants = new ArrayList<>();
                if (merchantStatus == null) {
                    merchantService.getAllMerchant().forEach(merchants::add);
                } else {
                    merchantService.getMerchantByStatus(merchantStatus).forEach(merchants::add);
                }

                if (merchants.isEmpty()) {
                    return new ResponseEntity<>(new ArrayList<Merchant>(), HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(merchants, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Operation(summary = "Create new Merchants with async")
    @PostMapping(value = "/create-merchants", produces = "application/json")
    public CompletableFuture<ResponseEntity<Merchant>> createMerchantAsync(@RequestBody Merchant merchant) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Merchant _merchant = new Merchant(merchant.getMerchantName(), merchant.getMerchantLocation(), merchant.getOpen());
                if (merchantService.addNewMerchant(_merchant)) {
                    return new ResponseEntity<>(_merchant, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Transactional(readOnly = true)
    @Operation(summary = "Get All Orders with Async")
    @GetMapping("/get-orders")
    public CompletableFuture<ResponseEntity<List<Orders>>> getAllOrdersAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<Orders> orders = ordersService.getAllOrders();
                if (orders.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(orders, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Transactional(readOnly = true)
    @Operation(summary = "Get Orders By Id with Async")
    @GetMapping("/get-orders/{id}")
    public CompletableFuture<ResponseEntity<Orders>> getOrdersByIdAsync(@PathVariable("id") long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Optional<Orders> userData = ordersService.getById(id);
                if (userData.isPresent()) {
                    return new ResponseEntity<>(userData.get(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Operation(summary = "Create New Orders with Async")
    @PostMapping("/create-orders")
    public CompletableFuture<ResponseEntity<Orders>> createOrderAsync(@RequestBody Orders order) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Orders _order = new Orders(order.getUsers(), order.getDestinationAddress(), order.getCompleted());
                if (ordersService.addNewOrder(_order)) {
                    return new ResponseEntity<>(_order, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Transactional(readOnly = true)
    @Operation(summary = "Get All Products Available with Async")
    @GetMapping("/get-products")
    public CompletableFuture<ResponseEntity<List<Product>>> getAllProductAsync() {
        return CompletableFuture.supplyAsync(() -> {
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
        });
    }


    @Transactional(readOnly = true)
    @Operation(summary = "Get Products By Id with Async")
    @GetMapping("/get-products/{id}")
    public CompletableFuture<ResponseEntity<Optional<Product>>> getProductByIdAsync(@PathVariable("id") String id) {
        return CompletableFuture.supplyAsync(() -> {
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
        });
    }

    @Operation(summary = "Create New Products with Async")
    @PostMapping("/create-products")
    public CompletableFuture<ResponseEntity<?>> createProductAsync(@RequestBody Product product) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Product _product = new Product(product.getProductName(), product.getPrice(), product.getMerchant());
                if (productService.addNewProduct(_product)) {
                    return new ResponseEntity<>(_product, HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>("Gagal Menambahkan Product", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (Exception e) {
                log.error("Error creating product: {}", e);
                return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Transactional(readOnly = true)
    @Operation(summary = "Get All Users with Async")
    @GetMapping("/get-users")
    public CompletableFuture<ResponseEntity<List<Users>>> getAllUserAsync(@RequestParam(required = false) String username) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                List<Users> users = new ArrayList<>();

                if (username == null) {
                    userService.findAllUsers().forEach(users::add);
                } else {
                    Optional<Users> user = userService.findByUsername(username);
                    if (user.isPresent()) {
                        users.add(user.get());
                    }
                }

                if (users.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(users, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }


    @Transactional(readOnly = true)
    @Operation(summary = "Get User By Id with Async")
    @GetMapping("/get-users/{id}")
    public CompletableFuture<ResponseEntity<Users>> getUserByIdAsync(@PathVariable("id") long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Optional<Users> userData = userService.findById(id);
                if (userData.isPresent()) {
                    return new ResponseEntity<>(userData.get(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Operation(summary = "Create New Users with Async")
    @PostMapping("/create-users")
    public CompletableFuture<ResponseEntity<Users>> createUserAsync(@RequestBody Users user) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Users _user = new Users(user.getUsername(), user.getEmailAddress(), user.getPassword());
                if (userService.addNewUser(_user)) {
                    return new ResponseEntity<>(_user, HttpStatus.CREATED);
                }
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Operation(summary = "Edit Users By Id with Async")
    @PutMapping("/edit-users/{id}")
    public CompletableFuture<ResponseEntity<Users>> updateUserAsync(@PathVariable("id") long id, @RequestBody Users user) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<Users> userData = userService.findById(id);
            try {
                if (userData.isPresent()) {
                    Users _user = userData.get();
                    _user.setUsername(user.getUsername());
                    _user.setPassword(user.getPassword());
                    _user.setEmailAddress(user.getEmailAddress());
                    if (userService.updateUserbyId(_user)) {
                        return new ResponseEntity<>(_user, HttpStatus.OK);
                    }
                }
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Operation(summary = "Delete Users By Id with Async")
    @DeleteMapping("/delete-users/{id}")
    public CompletableFuture<ResponseEntity<?>> deleteUserAsync(@PathVariable("id") long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (userService.deleteAllUser()) {
                    return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

    @Operation(summary = "Delete All Users with Async")
    @DeleteMapping("/delete-users")
    public CompletableFuture<ResponseEntity<?>> deleteAllUserAsync() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (userService.deleteAllUser()) {
                    return new ResponseEntity<>("User has been deleted", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        });
    }

}
