package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.DiscountRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.DiscountResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Discount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DiscountMapper {
    Discount toDiscount(DiscountRequest request);

    @Mapping(source = "maxUsagePerUser", target = "maxUsagePerUser")
    DiscountResponse toDiscountResponse(Discount discount);
    void toUpdateDiscount(@MappingTarget Discount discount, DiscountRequest request);
}
