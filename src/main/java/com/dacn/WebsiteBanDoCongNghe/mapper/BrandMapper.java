package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.BrandRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.CategoryRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.BrandResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CategoryResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Brand;
import com.dacn.WebsiteBanDoCongNghe.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toBrand(BrandRequest request);
    BrandResponse toBrandResponse(Brand brand);
    void updateBrand(@MappingTarget Brand brand, BrandRequest request);
}
