package com.binarfud.challenge7.controller;

import com.binarfud.challenge7.Controller.OrdersController;
import com.binarfud.challenge7.Model.Orders;
import com.binarfud.challenge7.Service.OrdersService;
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

public class OrdersControllerTest {

    @InjectMocks
    private OrdersController ordersController;

    @Mock
    private OrdersService ordersService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllOrders() {
        List<Orders> orders = new ArrayList<>();
        orders.add(new Orders());

        when(ordersService.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<Orders>> response = ordersController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetAllOrdersEmpty() {
        List<Orders> orders = new ArrayList<>();

        when(ordersService.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<Orders>> response = ordersController.getAllOrders();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetUserById() {
        long orderId = 1L;
        Orders order = new Orders();
        when(ordersService.getById(orderId)).thenReturn(Optional.of(order));

        ResponseEntity<Orders> response = ordersController.getUserById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetUserByIdNotFound() {
        long orderId = 1L;
        when(ordersService.getById(orderId)).thenReturn(Optional.empty());

        ResponseEntity<Orders> response = ordersController.getUserById(orderId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateOrder() {
        Orders order = new Orders();
        when(ordersService.addNewOrder(order)).thenReturn(true);

        ResponseEntity<Orders> response = ordersController.createOrder(order);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreateOrderError() {
        Orders order = new Orders();
        when(ordersService.addNewOrder(order)).thenReturn(false);

        ResponseEntity<Orders> response = ordersController.createOrder(order);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
