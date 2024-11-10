package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.VNPAYPaymentsResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Cart;
import com.dacn.WebsiteBanDoCongNghe.entity.Orders;
import com.dacn.WebsiteBanDoCongNghe.entity.User;
import com.dacn.WebsiteBanDoCongNghe.service.OrderService;
import com.dacn.WebsiteBanDoCongNghe.service.VNPAYPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class VNPAYPaymentController {
    VNPAYPaymentService paymentService;
    OrderService orderService;

    @GetMapping("/vn-pay")
    public ApiResponse<VNPAYPaymentsResponse> pay(HttpServletRequest request, Orders orders) {
        String transactionId = request.getParameter("vnp_TxnRef");
        return ApiResponse.<VNPAYPaymentsResponse>builder()
                .result(paymentService.createVnPayPayment(request,transactionId, orders))
                .build();
    }

    @GetMapping("/vn-pay-callback")
    public ApiResponse<VNPAYPaymentsResponse> payCallbackHandler(HttpServletRequest request) {
        if (orderService.payCallbackHandler(request)) {
            return ApiResponse.<VNPAYPaymentsResponse>builder()
                    .message("Thanh toán thành công")
                    .build();
        } else {
            return ApiResponse.<VNPAYPaymentsResponse>builder()
                    .message("Thanh toán không thành công")
                    .build();
        }
    }

}
