package com.binarfud.challenge7.Service;

import com.binarfud.challenge7.Model.OrdersDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {

    List<OrdersDetail> getAllOrderDetail();

    Boolean addNewOrderDetail(OrdersDetail orderDetail);
}
