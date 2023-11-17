package com.binarfud.challenge7.Model;

import com.binarfud.challenge7.Enum.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime orderTime;

    @Column(name = "destination_address", nullable = false, length = 255)
    private String destinationAddress;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @Enumerated(EnumType.STRING)
    private OrderStatus completed;

    @PrePersist
    public void setOrderTime() {
        this.orderTime = LocalDateTime.now();
    }

    public Orders(Users users, String destinationAddress, OrderStatus completed) {
        this.users = users;
        this.destinationAddress = destinationAddress;
        this.completed = completed;
    }
}
