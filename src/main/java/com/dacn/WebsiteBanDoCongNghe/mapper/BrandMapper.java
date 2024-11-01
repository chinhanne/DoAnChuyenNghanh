package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.BrandRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.BrandResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand toBrand(BrandRequest request);
    @Mapping(source = "id", target = "id")
    BrandResponse toBrandResponse(Brand brand);
    void updateBrand(@MappingTarget Brand brand, BrandRequest request);
}
