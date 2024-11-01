package com.dacn.WebsiteBanDoCongNghe.dto.request;

import com.dacn.WebsiteBanDoCongNghe.enums.DiscountScope;
import com.dacn.WebsiteBanDoCongNghe.enums.DiscountType;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountRequest {
    Double discountValue;
    DiscountType discountType;
    DiscountScope discountScope;
    Double minOrderAmount;
}
