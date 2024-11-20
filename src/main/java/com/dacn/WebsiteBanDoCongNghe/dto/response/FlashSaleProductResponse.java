package com.dacn.WebsiteBanDoCongNghe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlashSaleProductResponse {
    Long productId;
    Long flashSaleId;
    Double priceSale;
}
