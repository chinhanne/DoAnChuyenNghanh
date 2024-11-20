package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.request.FlashSaleRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.FlashSaleResponse;
import com.dacn.WebsiteBanDoCongNghe.service.FlashSaleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flashSale")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FlashSaleController {
    FlashSaleService flashSaleService;

    @PostMapping
    public ApiResponse<FlashSaleResponse> created(@RequestBody FlashSaleRequest request){
        return ApiResponse.<FlashSaleResponse>builder()
                .result(flashSaleService.createFlashSale(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<FlashSaleResponse> update(@RequestBody FlashSaleRequest request, @PathVariable Long id){
        return ApiResponse.<FlashSaleResponse>builder()
                .result(flashSaleService.updateFlashSale(id,request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<FlashSaleResponse>> getAll(){
        return ApiResponse.<List<FlashSaleResponse>>builder()
                .result(flashSaleService.getAllFlashSale())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<FlashSaleResponse> getFlashSaleById(@PathVariable Long id){
        return ApiResponse.<FlashSaleResponse>builder()
                .result(flashSaleService.getFlashSaleById(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleted(@PathVariable Long id){
        flashSaleService.deleteFlashSale(id);
        return ApiResponse.<String>builder()
                .result("Chương trình khuyến mãi đã được xóa")
                .build();
    }

}
