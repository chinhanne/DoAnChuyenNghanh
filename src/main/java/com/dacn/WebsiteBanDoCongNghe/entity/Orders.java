package com.dacn.WebsiteBanDoCongNghe.entity;

import com.dacn.WebsiteBanDoCongNghe.enums.OrderStatus;
import com.dacn.WebsiteBanDoCongNghe.enums.OrderStatusPayment;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    String transactionId;
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;
    Integer payment;
    @Enumerated(EnumType.STRING)
    OrderStatusPayment statusPayment;
    Double totalPrice;
    LocalDateTime oderDate;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderDetails> orderDetails;
}