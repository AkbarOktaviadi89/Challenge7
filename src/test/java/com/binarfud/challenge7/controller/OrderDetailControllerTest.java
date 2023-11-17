package com.binarfud.challenge7.controller;

import com.binarfud.challenge7.Controller.OrderDetailController;
import com.binarfud.challenge7.Model.OrdersDetail;
import com.binarfud.challenge7.Service.OrderDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class OrderDetailControllerTest {

    @InjectMocks
    private OrderDetailController orderDetailController;

    @Mock
    private OrderDetailService orderDetailService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllOrder() {
        List<OrdersDetail> orders = new ArrayList<>();
        orders.add(new OrdersDetail());

        when(orderDetailService.getAllOrderDetail()).thenReturn(orders);

        ResponseEntity<List<OrdersDetail>> response = orderDetailController.getAllOrder();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetAllOrderEmpty() {
        List<OrdersDetail> orders = new ArrayList<>();

        when(orderDetailService.getAllOrderDetail()).thenReturn(orders);

        ResponseEntity<List<OrdersDetail>> response = orderDetailController.getAllOrder();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testCreateOrderDetail() {
        OrdersDetail order = new OrdersDetail();
        when(orderDetailService.addNewOrderDetail(order)).thenReturn(true);

        ResponseEntity<OrdersDetail> response = orderDetailController.createOrderDetail(order);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreateOrderDetailError() {
        OrdersDetail order = new OrdersDetail();
        when(orderDetailService.addNewOrderDetail(order)).thenReturn(false);

        ResponseEntity<OrdersDetail> response = orderDetailController.createOrderDetail(order);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}
