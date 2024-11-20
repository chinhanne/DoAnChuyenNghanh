package com.dacn.WebsiteBanDoCongNghe.entity;

import com.dacn.WebsiteBanDoCongNghe.enums.FlashSaleStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
public class FlashSale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime startTimeSale;
    LocalDateTime endTimeSale;
    @Enumerated(EnumType.STRING)
    FlashSaleStatus status;
    Long discountPercentage;
    @OneToMany(mappedBy = "flashSale", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FlashSaleProduct> flashSaleProducts;

    @PrePersist
    @PreUpdate
    public void updateStatus() {
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(endTimeSale)) {
            this.status = FlashSaleStatus.EXPIRED;
        } else if (now.isBefore(startTimeSale)) {
            this.status = FlashSaleStatus.UPCOMING;
        } else {
            this.status = FlashSaleStatus.ACTIVE;
        }
    }
}
