package com.dacn.WebsiteBanDoCongNghe.dto.response;

import com.dacn.WebsiteBanDoCongNghe.enums.FlashSaleStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlashSaleResponse {
    Long id;
    LocalDateTime startTimeSale;
    LocalDateTime endTimeSale;
    FlashSaleStatus status;
    Long discountPercentage;
    List<FlashSaleProductResponse> flashSaleProductResponse;
}
