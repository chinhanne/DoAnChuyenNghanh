package com.dacn.WebsiteBanDoCongNghe.dto.request;

import com.dacn.WebsiteBanDoCongNghe.dto.response.RoleResponse;
import com.dacn.WebsiteBanDoCongNghe.validator.DobConstraint;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String firstname;
    String lastname;
    @Email(message = "EMAIL_INVALID_FORMAT")
    String email;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;
    @Min(value = 0, message = "MIN_GENDER")
    @Max(value = 1, message = "MAX_GENDER")
    Long gender;
    String address;
    String numberPhone;
    List<String> roles;
}
