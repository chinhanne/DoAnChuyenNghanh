package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.request.DiscountRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.DiscountResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.User;
import com.dacn.WebsiteBanDoCongNghe.service.DiscountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discount")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DiscountController {
    DiscountService discountService;

    @PostMapping
    public ApiResponse<DiscountResponse> created(@RequestBody @Valid DiscountRequest request){
        return ApiResponse.<DiscountResponse>builder()
                .result(discountService.createDiscount(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<DiscountResponse> updated(@PathVariable Long id, @RequestBody @Valid DiscountRequest request){
        return ApiResponse.<DiscountResponse>builder()
                .result(discountService.updateDiscount(id,request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<DiscountResponse>> getAll(){
        return ApiResponse.<List<DiscountResponse>>builder()
                .result(discountService.getAllDiscount())
                .build();
    }

    @GetMapping("/{code}")
    public ApiResponse<DiscountResponse> getDiscountByCode(@PathVariable String code){
        return ApiResponse.<DiscountResponse>builder()
                .result(discountService.getDiscountByCode(code))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleted(@PathVariable Long id){
        discountService.deleteDiscount(id);
        return ApiResponse.<String>builder()
                .result("Mã giảm giá đã được xóa")
                .build();
    }

}
