package com.binarfud.challenge7.service;

import com.binarfud.challenge7.Model.Orders;
import com.binarfud.challenge7.Repository.OrdersRepository;
import com.binarfud.challenge7.Service.Impl.OrdersServiceImpl;
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

public class OrdersServiceTest {

    @InjectMocks
    private OrdersServiceImpl ordersService;

    @Mock
    private OrdersRepository ordersRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        List<Orders> orders = new ArrayList<>();
        when(ordersRepository.findAll()).thenReturn(orders);

        List<Orders> result = ordersService.getAllOrders();

        assertEquals(orders, result);
        verify(ordersRepository).findAll();
    }

    @Test
    public void testGetOrderById() {
        Long orderId = 1L;
        Orders order = new Orders();
        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Orders> result = ordersService.getById(orderId);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
        verify(ordersRepository).findById(orderId);
    }

    @Test
    public void testAddNewOrder() {
        Orders order = new Orders();
        when(ordersRepository.save(order)).thenReturn(order);

        boolean result = ordersService.addNewOrder(order);

        assertTrue(result);
        verify(ordersRepository).save(order);
    }


    @Test
    public void testAddNewOrderFail() {
        Orders order = null;

        boolean result = ordersService.addNewOrder(order);

        assertFalse(result);
        verify(ordersRepository, never()).save(order);
    }
}
