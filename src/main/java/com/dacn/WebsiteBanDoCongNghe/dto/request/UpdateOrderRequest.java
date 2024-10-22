package com.dacn.WebsiteBanDoCongNghe.dto.request;

import com.dacn.WebsiteBanDoCongNghe.enums.OrderStatus;
import com.dacn.WebsiteBanDoCongNghe.enums.OrderStatusPayment;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderRequest {
    OrderStatus orderStatus;
    OrderStatusPayment orderStatusPayment;
}
