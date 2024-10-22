package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.request.CartItemRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CartItemResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CartResponse;
import com.dacn.WebsiteBanDoCongNghe.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CartController {
    CartService cartService;

//    Add cartItem
    @PostMapping
    public ApiResponse<CartResponse> created(@RequestBody CartItemRequest request){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.addProductToCart(request))
                .build();
    }

//    Get All cartItem
    @GetMapping
    public ApiResponse<List<CartItemResponse>> getAll(){
        return ApiResponse.<List<CartItemResponse>>builder()
                .result(cartService.getCartItems())
                .build();
    }

//    Delete Category
    @DeleteMapping("{cartItemId}")
    public ApiResponse<CartResponse> deleted(@PathVariable Long cartItemId){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.removeProductFromCart(cartItemId))
                .build();
    }

//    Increase cartItem
    @PostMapping("/increase/{cartItemId}")
    public ApiResponse<CartResponse> increaseQuantity(@PathVariable Long cartItemId){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.incrementCartItemQuantity(cartItemId))
                .build();
    }

//    Decrease cartItem
    @PostMapping("/decrease/{cartItemId}")
    public ApiResponse<CartResponse> decreaseQuantity(@PathVariable Long cartItemId){
        return ApiResponse.<CartResponse>builder()
                .result(cartService.decrementCartItemQuantity(cartItemId))
                .build();
    }

}
