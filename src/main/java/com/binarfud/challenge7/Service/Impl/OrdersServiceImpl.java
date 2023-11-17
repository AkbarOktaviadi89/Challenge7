package com.binarfud.challenge7.Service.Impl;

import com.binarfud.challenge7.Model.Orders;
import com.binarfud.challenge7.Repository.OrdersRepository;
import com.binarfud.challenge7.Service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public List<Orders> getAllOrders() {
        try {
            log.info("Retrieving All Orders.");
            return ordersRepository.findAll();
        } catch (Exception e) {
            log.error("Error while retrieving all Orders: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve Orders.", e);
        }
    }

    @Override
    public Optional<Orders> getById(Long id) {
        try {
            log.info("Retrieving Order by ID: {}", id);
            return ordersRepository.findById(id);
        } catch (Exception e) {
            log.error("Error while retrieving Order by ID: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve Order by ID.", e);
        }
    }


    @Override
    public Boolean addNewOrder(Orders order) {
        try {
            if (order != null) {
                log.info("Saving new Order...");
                ordersRepository.save(order);
                return true;
            } else {
                log.error("Failed to save new Order: order is null");
                return false;
            }
        } catch (Exception e) {
            log.error("Error while adding a new Order: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add a new Order.", e);
        }
    }
}

