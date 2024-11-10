package com.dacn.WebsiteBanDoCongNghe.dto.request;

import com.dacn.WebsiteBanDoCongNghe.validator.DobConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdatePasswordRequest {
    @Size(min = 8, message = "PASSWORD_INVALID")
    String currentPassword;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String confirmPassword;
}
