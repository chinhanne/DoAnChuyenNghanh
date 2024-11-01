package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.OrderDetailsRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.OrderRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.OrderDetailsResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.OrderResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Orders;
import com.dacn.WebsiteBanDoCongNghe.entity.OrderDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Orders toOrder(OrderRequest request);
    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "totalPrice", target = "totalPrice")
    @Mapping(source = "oderDate", target = "orderDate")
    @Mapping(source = "statusPayment", target = "statusPayment")
    @Mapping(source = "discount.code", target = "discountCode")
    OrderResponse toResponseOrder(Orders orders);
    OrderDetails toOrderDetails(OrderDetailsRequest request);
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(source = "orders.id", target = "orderId")
    OrderDetailsResponse toResponseOrderDetails(OrderDetails orderDetails);
}
