package com.binarfud.challenge7.Service.Impl;

import com.binarfud.challenge7.Model.OrdersDetail;
import com.binarfud.challenge7.Repository.OrderDetailRepository;
import com.binarfud.challenge7.Service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrdersDetail> getAllOrderDetail() {
        try {
            log.info("Retrieving All Order Detail.");
            return orderDetailRepository.findAll();
        } catch (Exception e) {
            log.error("Error while retrieving all Order Details: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to retrieve Order Details.", e);
        }
    }

    @Override
    public Boolean addNewOrderDetail(OrdersDetail orderDetail) {
        try {
            log.info("Adding a new Order Detail.");
            OrdersDetail newOrder = orderDetailRepository.save(orderDetail);
            if (newOrder != null) {
                return true;
            } else {
                log.error("Failed adding a new Order Detail");
                return false;
            }
        } catch (Exception e) {
            log.error("Error while adding a new OrderDetail: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add a new OrderDetail.", e);
        }
    }
}
