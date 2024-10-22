package com.dacn.WebsiteBanDoCongNghe.dto.response;

import com.dacn.WebsiteBanDoCongNghe.dto.request.CartItemRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    Long id;
    String userName;
    List<CartItemResponse> cartItems;
    Double totalPrice;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
