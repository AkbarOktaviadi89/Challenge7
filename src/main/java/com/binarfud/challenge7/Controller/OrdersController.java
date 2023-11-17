package com.binarfud.challenge7.Controller;

import com.binarfud.challenge7.Model.Orders;
import com.binarfud.challenge7.Service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Transactional
@CrossOrigin(origins = "localhost:3000")
@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @Operation(summary = "Get All Orders")
    @GetMapping("/get-orders")
    public ResponseEntity<List<Orders>> getAllOrders() {
        try {
            List<Orders> orders = ordersService.getAllOrders();
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get Orders By Id")
    @GetMapping("/get-orders/{id}")
    public ResponseEntity<Orders> getUserById(@PathVariable("id") long id) {
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
    }

    @Operation(summary = "Create New Orders")
    @PostMapping("/create-orders")
    public ResponseEntity<Orders> createOrder(@RequestBody Orders order) {
        try {
            Orders _order = new Orders(order.getUsers(), order.getDestinationAddress(), order.getCompleted());
            if (ordersService.addNewOrder(_order)) {
                return new ResponseEntity<>(_order, HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
