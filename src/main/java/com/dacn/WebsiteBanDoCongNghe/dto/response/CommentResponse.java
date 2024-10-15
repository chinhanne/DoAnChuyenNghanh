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
    String productName;
    String userName;
    String content;
    List<ChildCommentResponse> childComments;
    boolean display;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;
}
