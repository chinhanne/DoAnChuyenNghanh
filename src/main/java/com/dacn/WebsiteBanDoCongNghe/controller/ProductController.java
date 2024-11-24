package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.request.ProductRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.ProductUpdatedRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.ProductUpdatedStatusAndQuantityRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.SearchRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ProductResponse;
import com.dacn.WebsiteBanDoCongNghe.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductController {
    ProductService productService;

//    Create product
    @PostMapping("/create")
    public ApiResponse<ProductResponse> create(@ModelAttribute @Valid ProductRequest request) throws IOException {

        return ApiResponse.<ProductResponse>builder()
                .result(productService.createdProduct(request))
                .build();
    }

//    Get all product
    @GetMapping
    public ApiResponse<List<ProductResponse>> getAll(){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProduct())
                .build();
    }

//    Get product by id
    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long id){
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(id))
                .build();
    }

//    Updated Product
    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable Long id, @ModelAttribute @Valid ProductUpdatedRequest request) throws IOException {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(id,request))
                .build();
    }

    //    Updated quantity and status product
    @PutMapping("/update-quantity-status/{id}")
    public ApiResponse<ProductResponse> updateQuantityAndStatus(@PathVariable Long id, @RequestBody ProductUpdatedStatusAndQuantityRequest request) throws IOException {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateQuantityAndStatus(id,request))
                .build();
    }

//    Search Product
    @GetMapping("/search")
    public ApiResponse<List<ProductResponse>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) Double price
    ){
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setName(name);
        searchRequest.setCategoryName(categoryName);
        searchRequest.setBrandName(brandName);
        searchRequest.setPrice(price);

        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getProductFilter(searchRequest))
                .build();
    }

    @GetMapping("/header-search")
    public ApiResponse<List<ProductResponse>> searchHeader(@RequestParam(required = false) String keyword){
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.searchByKeyword(keyword))
                .build();
    }

//    Delete product
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ApiResponse.<String>builder().result("Xóa sản phẩm thành công").build();
    }

}
