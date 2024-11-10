package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.request.BrandRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.BrandResponse;
import com.dacn.WebsiteBanDoCongNghe.service.BrandService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class BrandController {
    BrandService brandService;

//    Add brand
    @PostMapping
    public ApiResponse<BrandResponse> created(@RequestBody @Valid BrandRequest request){
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.createdBrand(request))
                .build();
    }

//    Update brand
    @PutMapping("/{id}")
    public ApiResponse<BrandResponse> updated(@PathVariable Long id, @RequestBody @Valid BrandRequest request){
        return ApiResponse.<BrandResponse>builder()
                .result(brandService.updatedBrand(id,request))
                .build();
    }

//    Get All Brand
    @GetMapping
    public ApiResponse<List<BrandResponse>> getAll(){
        return ApiResponse.<List<BrandResponse>>builder()
                .result(brandService.getAllBrand())
                .build();
    }

//    Delete Brand
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleted(@PathVariable Long id){
        brandService.deleteBrand(id);
        return ApiResponse.<String>builder()
                .result("Hãng đã được xóa")
                .build();
    }

}
