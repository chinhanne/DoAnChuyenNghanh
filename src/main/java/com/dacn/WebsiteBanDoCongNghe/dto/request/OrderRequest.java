package com.dacn.WebsiteBanDoCongNghe.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    List<OrderDetailsRequest> orderDetails;
    @Min(value = 0, message = "MIN_ORDER")
    @Max(value = 1, message = "MAX_ORDER")
    Integer payment;
    Long discountId;
}
