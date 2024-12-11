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
public class CommentResponse {
    Long id;
    Long parentId;  // Thêm trường này để lưu parentId
    String productName;
    String userName;
    String content;
    String userId;
    List<ChildCommentResponse> childComments;
    boolean display;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;
}
