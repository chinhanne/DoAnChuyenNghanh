package com.dacn.WebsiteBanDoCongNghe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name", unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
    String name;
    LocalDateTime dateCreated;
    LocalDateTime dateUpdated;

    //    Khởi tạo thời gian tạo comment
    @PrePersist
    public void prePersist() {
        this.dateCreated = LocalDateTime.now();
        this.dateUpdated = LocalDateTime.now();
    }

    //    Khởi tạo thời gian update comment
    @PreUpdate
    public void preUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }
}
