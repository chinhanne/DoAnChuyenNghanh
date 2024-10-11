package com.dacn.WebsiteBanDoCongNghe.dto.response;

import com.dacn.WebsiteBanDoCongNghe.entity.Image;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    String name;
    double price;
    String description;
    List<String> images;
    String categoryName;
    LocalDate dateCreated;
    LocalDate dateUpdated;
    String brandName;
}
