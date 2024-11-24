package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.VNPAYPaymentsResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Orders;
import com.dacn.WebsiteBanDoCongNghe.service.OrderService;
import com.dacn.WebsiteBanDoCongNghe.service.VNPAYPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean paymentSuccessful = orderService.payCallbackHandler(request);

        if (paymentSuccessful) {
            // Chuyển hướng đến trang thanh toán thành công
            response.sendRedirect("http://localhost:3000/pay_success");
        } else {
            // Chuyển hướng đến trang thanh toán thất bại
            response.sendRedirect("http://localhost:3000/pay_failure");
        }
    }


}
