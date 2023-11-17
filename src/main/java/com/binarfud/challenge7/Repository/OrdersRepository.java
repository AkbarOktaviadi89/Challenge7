package com.binarfud.challenge7.Repository;

import com.binarfud.challenge7.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("SELECT o FROM Orders o WHERE o.orderId = :orderId")
    Optional<Orders> findByOrderId(Long orderId);
}
