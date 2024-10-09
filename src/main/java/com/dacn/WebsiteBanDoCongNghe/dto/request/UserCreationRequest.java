package com.dacn.WebsiteBanDoCongNghe.dto.request;

import com.dacn.WebsiteBanDoCongNghe.validator.DobConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotBlank(message = "USERNAME_NOT_NULL")
    @Size(min = 4, message = "USERNAME_INVALID")
    String username;

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
}
