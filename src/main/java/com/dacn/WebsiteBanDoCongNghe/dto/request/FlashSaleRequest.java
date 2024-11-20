package com.dacn.WebsiteBanDoCongNghe.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlashSaleRequest {
    LocalDateTime startTimeSale;
    LocalDateTime endTimeSale;
    Long discountPercentage;
    List<FlashSaleProductRequest> flashSaleProducts;
}
