package com.binarfud.challenge7.Controller;

import com.binarfud.challenge7.Model.OrdersDetail;
import com.binarfud.challenge7.Service.OrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/order-details")
public class OrderDetailController {

    @Autowired
    OrderDetailService orderDetailService;

    @Transactional(readOnly = true)
    @Operation(summary = "Get All Order Details")
    @GetMapping("/get-orderDetails")
    public ResponseEntity<List<OrdersDetail>> getAllOrder(){
        try {
            List<OrdersDetail> orders = orderDetailService.getAllOrderDetail();
            if(orders.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Create new Order Details")
    @PostMapping("/create-orderDetails")
    public ResponseEntity<OrdersDetail> createOrderDetail(@RequestBody OrdersDetail order){
        try {
            OrdersDetail _order = new OrdersDetail(order.getQuantity(), order.getTotalPrice(), order.getOrders(), order.getProduct());
            if(orderDetailService.addNewOrderDetail(_order)){
            return new ResponseEntity<>(_order, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
