package com.dacn.WebsiteBanDoCongNghe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String username;
    String firstname;
    String lastname;
    String email;
    LocalDate dob;
    Long gender;
    String address;
    String numberPhone;
    String image;
    Set<RoleResponse> roles;
}
