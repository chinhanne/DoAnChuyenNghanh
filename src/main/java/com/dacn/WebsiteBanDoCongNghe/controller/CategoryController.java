package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.request.CategoryRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.BrandResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CategoryResponse;
import com.dacn.WebsiteBanDoCongNghe.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CategoryController {
    CategoryService categoryService;

//    Add category
    @PostMapping("/add")
    public ApiResponse<CategoryResponse> created(@ModelAttribute @Valid CategoryRequest request){
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createdCategory(request))
                .build();
    }

//    Update Category
    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updated(@PathVariable Long id, @ModelAttribute @Valid CategoryRequest request)
            throws IOException{
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updatedCategory(id,request))
                .build();
    }

    //    Restore category isDelete
    @PutMapping("/restore/{id}")
    public ApiResponse<String> restoreCategory(@PathVariable Long id) {
        categoryService.restoreCategory(id);
        return ApiResponse.<String>builder()
                .result("Thể loại đã được khôi phục thành công")
                .build();
    }

//    Get All Category
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAll(){
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAllCategory())
                .build();
    }

    //    Get all category delete soft
    @GetMapping("/list-category-delete-soft")
    public ApiResponse<List<CategoryResponse>> listCategorySoft(){
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAllCategoryForIsDelete())
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

//    Delete Soft Category
    @DeleteMapping("/delete-soft-category/{id}")
    public ApiResponse<String> deletedSoftCategory(@PathVariable Long id){
        categoryService.deletedSoftCategory(id);
        return ApiResponse.<String>builder()
                .result("Thể loại đã được xóa")
                .build();
    }

}
