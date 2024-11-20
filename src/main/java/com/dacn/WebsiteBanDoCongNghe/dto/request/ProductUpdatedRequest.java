package com.dacn.WebsiteBanDoCongNghe.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductUpdatedRequest {
    @NotBlank(message = "PRODUCT_NAME_NOT_NULL")
    String name;
    @Min(value = 0, message = "PRICE_PRODUCT_NOT_NULL")
    double price;
    @Min(value = 0, message = "QUANTITY_PRODUCT_NOT_NULL")
    Long quantity;
    String status;
    String description;
    List<MultipartFile> images;
    Long categoryId;
    Long brandId;
    String chip;
    String card;
    String ram;
    String screen;
}
