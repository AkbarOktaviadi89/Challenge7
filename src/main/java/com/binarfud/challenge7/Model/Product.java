package com.binarfud.challenge7.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name ="product_code")
    private String productCode;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "merchant_code")
    private Merchant merchant;

    public Product(String productName, Double price, Merchant merchant) {
        this.productName = productName;
        this.price = price;
        this.merchant = merchant;
    }

}