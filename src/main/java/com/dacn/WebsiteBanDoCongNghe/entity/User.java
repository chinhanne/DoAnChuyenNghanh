package com.dacn.WebsiteBanDoCongNghe.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    //    unicode_ci dùng để không phân biệt hoa và thường
    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String username;
    String password;
    String firstname;
    String lastname;
    String email;
    LocalDate dob;
    @Min(value = 0, message = "MIN_GENDER")
    @Max(value = 1, message = "MAX_GENDER")
    Long gender;
    String address;
    String numberPhone;
    @ManyToMany
    Set<Role> roles;
}