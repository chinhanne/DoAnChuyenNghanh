package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.request.BrandRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.BrandResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ProductResponse;
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

//    Restore brand isDelete
    @PutMapping("/restore/{id}")
    public ApiResponse<String> restoreBrand(@PathVariable Long id) {
        brandService.restoreBrand(id);
        return ApiResponse.<String>builder()
                .result("Hãng đã được khôi phục thành công")
                .build();
    }

//    Get All Brand
    @GetMapping
    public ApiResponse<List<BrandResponse>> getAll(){
        return ApiResponse.<List<BrandResponse>>builder()
                .result(brandService.getAllBrand())
                .build();
    }

//    Get all brand delete soft
    @GetMapping("/list-brand-delete-soft")
    public ApiResponse<List<BrandResponse>> listBrandsSoft(){
        return ApiResponse.<List<BrandResponse>>builder()
                .result(brandService.getAllBrandForIsDelete())
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
//    Delete Soft Brand
    @DeleteMapping("/delete-soft-brand/{id}")
    public ApiResponse<String> deletedSoftBrand(@PathVariable Long id){
        brandService.deletedSoftBrand(id);
        return ApiResponse.<String>builder()
                .result("Brand đã được xóa")
                .build();
    }

}
