package com.binarfud.challenge7.Model;

import com.binarfud.challenge7.Enum.MerchantStatus;
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
@Table(name = "merchant")
public class Merchant {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name ="merchant_code")
    private String merchantCode;

    @Column(name = "merchant_name")
    private String merchantName;

    @Column(name = "merchant_location")
    private String merchantLocation;

    @Enumerated(EnumType.STRING)
    private MerchantStatus open;

    public Merchant(String merchantName, String merchantLocation, MerchantStatus open) {
        this.merchantName = merchantName;
        this.merchantLocation = merchantLocation;
        this.open = open;
    }

}