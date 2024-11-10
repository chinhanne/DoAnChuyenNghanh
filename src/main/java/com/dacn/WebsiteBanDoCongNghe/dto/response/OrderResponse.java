package com.dacn.WebsiteBanDoCongNghe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {

    String userName;
    List<OrderDetailsResponse> orderDetails;
    String orderStatus;
    Integer payment;
    Double totalPrice;
    LocalDate orderDate;
    String paymentUrl;
    String statusPayment;
    String transactionId;
    String discountCode;
}
