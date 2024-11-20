package com.dacn.WebsiteBanDoCongNghe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRequest {
    @NotNull(message = "PRODUCT_ID_NOT_NULL")
    Long productId;
    @NotNull(message = "USER_ID_NOT_NULL")
    String userId;
    Long parentCommentId;
    @NotBlank(message = "CONTENT_COMMENT_NOT_NULL")
    String content;
}
