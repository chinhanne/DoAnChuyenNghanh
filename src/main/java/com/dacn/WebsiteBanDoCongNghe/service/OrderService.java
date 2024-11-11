package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.configuration.VnPayConfig;
import com.dacn.WebsiteBanDoCongNghe.dto.request.*;
import com.dacn.WebsiteBanDoCongNghe.dto.response.*;
import com.dacn.WebsiteBanDoCongNghe.entity.*;
import com.dacn.WebsiteBanDoCongNghe.enums.OrderStatus;
import com.dacn.WebsiteBanDoCongNghe.enums.OrderStatusPayment;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.mapper.MonthlyRevenueMapper;
import com.dacn.WebsiteBanDoCongNghe.mapper.OrderMapper;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.*;
import com.dacn.WebsiteBanDoCongNghe.utils.VnPayUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    UserReponsitory userReponsitory;
    VNPAYPaymentService vnpayPaymentService;
    DiscountService discountService;
    MonthlyRevenueMapper monthlyRevenueMapper;
    ProductReponsitory productReponsitory;

//    Kiểm tra sự tồn tại của user
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }

        String username = authentication.getName();
        return userReponsitory.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

//    Tạo trang order
    public OrderResponse checkout(OrderRequest request, HttpServletRequest httpServletRequest){
        User user = getAuthenticatedUser();

        Orders orders = orderMapper.toOrder(request);
        orders.setUser(user);
        orders.setOrderStatus(OrderStatus.CHO_XU_LY);
        orders.setOrderDate(LocalDate.now());
        orders.setStatusPayment(OrderStatusPayment.DANG_CHO_XU_LY);

        String transactionId = VnPayUtils.generateTransactionIdWithDate(8);
        orders.setTransactionId(transactionId);

        List<OrderDetails> orderDetails = request.getOrderDetails().stream()
                .map(orderDetailsRequest -> {
                    if(orderDetailsRequest.getProductId() == null) {
                        throw new AppException(ErrorCode.PRODUCT_NOT_EXISTED);
                    }

                    Product product = productReponsitory.findById(orderDetailsRequest.getProductId())
                            .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

                    if(product.getQuantity() < orderDetailsRequest.getQuantity()){
                        throw new AppException(ErrorCode.QUANTITY_PRODUCT_NOT_ENOUGH);
                    }
                    product.setQuantity(product.getQuantity() - orderDetailsRequest.getQuantity());
                    product.setStatus(product.getQuantity() == 0 ? "Hết hàng" : "Còn hàng");
                    productReponsitory.save(product);


                    OrderDetails orderDetail = orderMapper.toOrderDetails(orderDetailsRequest);
                    orderDetail.setProduct(product);
                    orderDetail.setQuantity(orderDetailsRequest.getQuantity());
                    orderDetail.setTotalPrice(orderDetailsRequest.getProductPrice() * orderDetailsRequest.getQuantity());
                    orderDetail.setOrders(orders);
                    return orderDetail;
                })
                .collect(Collectors.toList());

        orders.setOrderDetails(orderDetails);


        if(request.getDiscountId() != null){
            discountService.applyDiscount(request.getDiscountId(), user,orders);
        }
        orders.setTotalPrice(request.getAmount());

        if (request.getPayment() == 1) {
            VNPAYPaymentsResponse vnpayPaymentsResponse = vnpayPaymentService.createVnPayPayment(httpServletRequest, transactionId, orders);
            orders.setPayment(1);
            orderRepository.save(orders);

            OrderResponse orderResponse = orderMapper.toResponseOrder(orders); // Chuyển đổi đơn hàng thành phản hồi
            orderResponse.setPaymentUrl(vnpayPaymentsResponse.getPaymentUrl()); // Thiết lập paymentUrl
            orderResponse.setTransactionId(transactionId);
            orderResponse.setDiscountCode(orders.getDiscount() != null ? orders.getDiscount().getCode() : null);
            return orderResponse;
        } else {
            orders.setPayment(0);
            orderRepository.save(orders);

            return orderMapper.toResponseOrder(orders);
        }
    }

    public boolean payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        String orderId = request.getParameter("vnp_TxnRef");

        Orders orders = orderRepository.findByTransactionId(orderId).orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));

        if ("00".equals(status)) {
            orders.setStatusPayment(OrderStatusPayment.THANH_TOAN_THANH_CONG);
            orderRepository.save(orders);
            return true;
        }else{
            orders.setStatusPayment(OrderStatusPayment.THANH_TOAN_THAT_BAI);
            orders.setOrderStatus(OrderStatus.DA_HUY);
            orderRepository.save(orders);
            return false;
        }
    }

//    Thống kê doanh thu
    public MonthlyRevenueResponse getMonthlyRevenue(MonthlyRevenueRequest request){
        int month = request.getMonth();
        int year = request.getYear();

        YearMonth yearMonth = YearMonth.of(year,month);
        LocalDate startOfMonth = yearMonth.atDay(1);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();

        List<Orders>  orders = orderRepository.findByOrderDateBetween(startOfMonth,endOfMonth);
        List<WeeklyRevenue> weeklyRevenues = new ArrayList<>();
        double totalRevenue = 0.0;

        for(int week = 1; week <= 4; week++){
            LocalDate weekStart = startOfMonth.plusDays((week - 1) * 7);
            LocalDate weekEnd = startOfMonth.plusDays(6).isAfter(endOfMonth) ? endOfMonth : weekStart.plusDays(6);

            double revenue = orders.stream()
                    .filter(order -> !order.getOrderDate().isBefore(weekStart) && !order.getOrderDate().isAfter(weekEnd))
                    .mapToDouble(Orders::getTotalPrice)
                    .sum();

            weeklyRevenues.add(new WeeklyRevenue(week, revenue));
            totalRevenue += revenue;

        }

        MonthlyRevenueResponse monthlyRevenueResponse = monthlyRevenueMapper.toMonthlyRevenueResponse(request);
        monthlyRevenueResponse.setWeeklyRevenue(weeklyRevenues);
        monthlyRevenueResponse.setTotalRevenue(totalRevenue);

        return monthlyRevenueResponse;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public OrderResponse updateOrderStatus(UpdateOrderRequest request, Long orderId){
        Orders orders = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED));

        if (request.getOrderStatus() != null) {
            orders.setOrderStatus(request.getOrderStatus());
        }

        if (request.getOrderStatusPayment() != null) {
            orders.setStatusPayment(request.getOrderStatusPayment());
        }
        orderRepository.save(orders);
        return orderMapper.toResponseOrder(orders);

    }

//    Get all Orders
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getAllOrders(){
        return orderRepository.findAll().stream().map(orderMapper::toResponseOrder).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public OrderResponse getOrderById(Long orderId){
        return orderMapper.toResponseOrder(orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_EXISTED)));
    }

//    Delete order
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteOrder(Long orderId){
        if(!orderRepository.existsById(orderId)){
            throw new AppException(ErrorCode.ORDER_NOT_EXISTED);
        }
        orderRepository.deleteById(orderId);
    }
}
