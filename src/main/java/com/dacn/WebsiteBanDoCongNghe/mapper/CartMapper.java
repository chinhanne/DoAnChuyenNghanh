package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.CartItemRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CartItemResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CartResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Cart;
import com.dacn.WebsiteBanDoCongNghe.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", ignore = true)
    CartItem toCartItem(CartItemRequest request);

    @Mapping(source = "user.username", target = "userName")
    CartResponse toResponseCart(Cart cart);

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    @Mapping(source = "cart.id", target = "cartId")
    CartItemResponse toResponseCartItem(CartItem cartItem);
}
