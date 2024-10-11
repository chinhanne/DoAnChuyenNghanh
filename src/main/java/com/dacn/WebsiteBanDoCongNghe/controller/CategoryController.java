package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.request.CategoryRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CategoryResponse;
import com.dacn.WebsiteBanDoCongNghe.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

//    Add category
    @PostMapping("/add")
    public ApiResponse<CategoryResponse> created(@RequestBody @Valid CategoryRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createdCategory(request))
                .build();
    }

//    Update Category
    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updated(@PathVariable Long id, @RequestBody @Valid CategoryRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updatedCategory(id,request))
                .build();
    }

//    Get All Category
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAll(){
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAllCategory())
                .build();
    }

//    Delete Category
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleted(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ApiResponse.<String>builder()
                .result("Thể loại đã được xóa")
                .build();
    }

}
