package com.dacn.WebsiteBanDoCongNghe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_id", nullable = false)
    Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;
    Integer quantity;
    Double totalPrice;
    @ManyToOne
    @JoinColumn(name = "discount_id") 
    Discount discount;
}
