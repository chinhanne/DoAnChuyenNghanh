package com.dacn.WebsiteBanDoCongNghe.entity;

import com.dacn.WebsiteBanDoCongNghe.enums.DiscountScope;
import com.dacn.WebsiteBanDoCongNghe.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true, nullable = false)
    String code;

    Double discountValue;
    LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    DiscountType discountType;
    @Enumerated(EnumType.STRING)
    DiscountScope discountScope;

    Integer maxUsagePerUser = 1;
    Double minOrderAmount;

    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
    List<DiscountUsage> discountUsages = new ArrayList<>();
}
