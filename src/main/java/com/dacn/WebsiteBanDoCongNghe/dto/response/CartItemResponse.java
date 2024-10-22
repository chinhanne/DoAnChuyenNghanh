package com.dacn.WebsiteBanDoCongNghe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    Long cartId;
    String productName;
    Double productPrice;
    Integer quantity;
    Double totalPrice;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
