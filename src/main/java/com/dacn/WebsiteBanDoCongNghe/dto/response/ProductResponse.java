package com.dacn.WebsiteBanDoCongNghe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    double price;
    double priceSale;
    String description;
    List<String> images;
    String categoryName;
    LocalDate dateCreated;
    LocalDate dateUpdated;
    String brandName;
    Long quantity;
    String status;
    String chip;
    String card;
    String ram;
    String screen;
}
