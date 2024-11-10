package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.MonthlyRevenueRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.MonthlyRevenueResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MonthlyRevenueMapper {
    MonthlyRevenueResponse toMonthlyRevenueResponse(MonthlyRevenueRequest request);
}
