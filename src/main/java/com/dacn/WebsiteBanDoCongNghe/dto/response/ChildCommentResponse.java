package com.dacn.WebsiteBanDoCongNghe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChildCommentResponse {
    String userName;
    String content;
    boolean display;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
