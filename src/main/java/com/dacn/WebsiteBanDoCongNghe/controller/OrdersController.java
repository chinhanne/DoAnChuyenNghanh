package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.request.MonthlyRevenueRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.OrderRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.UpdateOrderRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.MonthlyRevenueResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.OrderResponse;
import com.dacn.WebsiteBanDoCongNghe.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrdersController {
    OrderService orderService;

//    checkout
    @PostMapping
    public ApiResponse<OrderResponse> checkout(@RequestBody OrderRequest request, HttpServletRequest httpRequest){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.checkout(request, httpRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<List<OrderResponse>> getAllOrder(){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getAllOrders())
                .build();
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long orderId){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrderById(orderId))
                .build();
    }

    @GetMapping("/history/orders-user")
    public ApiResponse<List<OrderResponse>> getHistoryOrdersByUser(){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getHistoryOrderByUser())
                .build();
    }

    @PutMapping("/{orderId}")
    public ApiResponse<OrderResponse> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrderStatus(request,orderId))
                .build();
    }

    @DeleteMapping("/{orderId}")
    public ApiResponse<String> updateOrderStatus(@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
        return ApiResponse.<String>builder()
                .result("Xóa đơn hàng thành công.")
                .build();
    }

    @PostMapping("/monthly")
    public ApiResponse<MonthlyRevenueResponse> getMonthlyRevenue(@RequestBody MonthlyRevenueRequest request) {
       return ApiResponse.<MonthlyRevenueResponse>builder()
               .result(orderService.getMonthlyRevenue(request))
               .build();
    }
}
