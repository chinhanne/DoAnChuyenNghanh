package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.FlashSaleProductRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.FlashSaleRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.OrderDetailsRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.OrderRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.FlashSaleProductResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.FlashSaleResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.OrderDetailsResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.OrderResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.FlashSale;
import com.dacn.WebsiteBanDoCongNghe.entity.FlashSaleProduct;
import com.dacn.WebsiteBanDoCongNghe.entity.OrderDetails;
import com.dacn.WebsiteBanDoCongNghe.entity.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlashSaleMapper {
    FlashSale toFlashSale(FlashSaleRequest request);
    @Mapping(target = "flashSaleProductResponse", source = "flashSaleProducts")
    FlashSaleResponse toResponseFlashSale(FlashSale flashSale);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "flashSale", ignore = true)
    FlashSaleProduct toFlashSaleProduct(FlashSaleProductRequest request);

    @Mapping(target = "flashSaleId", source = "flashSale.id")
    @Mapping(target = "productId", source = "product.id")
    FlashSaleProductResponse toFlashSaleProductResponse(FlashSaleProduct flashSaleProduct);

}
