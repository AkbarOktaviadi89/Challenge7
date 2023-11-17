package com.binarfud.challenge7.Service;

import com.binarfud.challenge7.Model.Orders;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrdersService {
    List<Orders> getAllOrders();

    Optional<Orders> getById(Long id);

    Boolean addNewOrder(Orders order);
}
