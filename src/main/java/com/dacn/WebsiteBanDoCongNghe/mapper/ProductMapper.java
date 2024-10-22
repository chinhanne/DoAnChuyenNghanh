package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.ProductRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.ProductUpdatedRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ProductResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Image;
import com.dacn.WebsiteBanDoCongNghe.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest request);

    // Ánh xạ từ category.name sang categoryName
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "brand.name", target = "brandName")
    ProductResponse toProductResponse(Product product);

    @Mapping(target = "numberPhone", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    void toUpdateProduct(@MappingTarget Product product, ProductUpdatedRequest request);

//    Chuyển danh sách ảnh thành danh sách đường dẫn
    default List<String> mapImagesToPaths(List<Image> images) {
        if (images == null) {
            return null; // Trả về null nếu danh sách ảnh null
        }
        return images.stream()
                .map(image -> image.getImagePath())
                .collect(Collectors.toList());
    }
}
