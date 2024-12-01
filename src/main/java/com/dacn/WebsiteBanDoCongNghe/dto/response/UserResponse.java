package com.dacn.WebsiteBanDoCongNghe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String email;
    LocalDate dob;
    Long gender;
    Boolean noPassword;
    String address;
    String numberPhone;
    String image;
    Set<RoleResponse> roles;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;
    Boolean isStatus;
}
