package com.dacn.WebsiteBanDoCongNghe.dto.request;

import com.dacn.WebsiteBanDoCongNghe.dto.response.RoleResponse;
import com.dacn.WebsiteBanDoCongNghe.validator.DobConstraint;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @NotBlank(message = "PASSWORD_NOT_NULL")
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String firstname;
    String lastname;
    @NotBlank(message = "EMAIL_NOT_NULL")
    @Email(message = "EMAIL_INVALID_FORMAT")
    String email;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
    @Min(value = 0, message = "MIN_GENDER")
    @Max(value = 1, message = "MAX_GENDER")
    Long gender;
    @NotBlank(message = "ADDRESS_NOT_NULL")
    String address;
    @NotBlank(message = "NUMBERPHONE_NOT_NULL")
    String numberPhone;
    List<String> roles;
}
