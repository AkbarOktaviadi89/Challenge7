package com.binarfud.challenge7.Repository;

import com.binarfud.challenge7.Model.OrdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrdersDetail, Long> {

//    @Query("select u.username, p.productName, od.quantity from OrdersDetail od\n" +
//            "join Orders o on o.orderId = od.orders\n" +
//            "join Users u on u.userId = o.users\n" +
//            "join Product p on p.productCode = od.product\n" +
//            "join Merchant m on m.merchantCode = p.merchant\n" +
//            "where od.orders = o.users")
//
//    List<OrdersDetail> getCustomer();
}
