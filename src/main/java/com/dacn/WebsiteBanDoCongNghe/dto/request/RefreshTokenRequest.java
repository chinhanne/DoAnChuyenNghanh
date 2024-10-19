package com.dacn.WebsiteBanDoCongNghe.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenRequest {
    private String token; // Hoặc tên khác mà bạn muốn
}
