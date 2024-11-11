package com.dacn.WebsiteBanDoCongNghe.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdatedStatus {
    Long quantity;
    String status;

}
