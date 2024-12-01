package com.dacn.WebsiteBanDoCongNghe.dto.response;

import com.dacn.WebsiteBanDoCongNghe.enums.DiscountScope;
import com.dacn.WebsiteBanDoCongNghe.enums.DiscountType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DiscountResponse {
    Long id;
    String code;
    Double discountValue;
    LocalDate expirationDate;
    DiscountType discountType;
    DiscountScope discountScope;
    Integer maxUsagePerUser;
    Double minOrderAmount;
    Boolean isDelete;
}
