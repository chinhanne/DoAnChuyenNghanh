package com.dacn.WebsiteBanDoCongNghe.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailsRequest {
    Long productId;
    Integer quantity;
    Double productPrice;
}
