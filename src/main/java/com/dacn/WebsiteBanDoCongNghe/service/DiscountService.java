package com.dacn.WebsiteBanDoCongNghe.service;


import com.dacn.WebsiteBanDoCongNghe.dto.request.DiscountRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.DiscountResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.*;
import com.dacn.WebsiteBanDoCongNghe.enums.DiscountScope;
import com.dacn.WebsiteBanDoCongNghe.enums.DiscountType;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.mapper.DiscountMapper;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.DiscountRepository;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.DiscountUsageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class DiscountService {
    DiscountRepository discountRepository;
    DiscountUsageRepository discountUsageRepository;
    DiscountMapper discountMapper;

// Hàm generate mã code ngẫu nhiên
    public String generateDiscountCode(Discount discount){
        String prefix = discount.getDiscountScope() == DiscountScope.ORDER ? "ORD" : "PRO";

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyHHmm");
        String expirationDate = discount.getExpirationDate().format(dateTimeFormatter);

        String discountValue;
        if(discount.getDiscountType() == DiscountType.PERCENTAGE){
            discountValue = String.format("%02d", discount.getDiscountValue().intValue()) + "%";
        }else{
            discountValue = String.format("%02d", discount.getDiscountValue().intValue()) + "K";
        }
        return prefix + expirationDate + discountValue;
    }


    public DiscountResponse applyDiscount(Long discountId, User user, Orders order){
        Discount discount = discountRepository.findById(discountId).orElseThrow(() ->
                new AppException(ErrorCode.DISCOUNT_NOT_EXISTED));

//        Kiểm tra thời gian hết hạn
        if(LocalDateTime.now().isAfter(discount.getExpirationDate())){
            throw new AppException(ErrorCode.TIME_EXPIRED);
        }

//        Kiểm tra số lần sử dụng
        long usageCount = discountUsageRepository.countByDiscountAndUser(discount,user);
        if(usageCount >= discount.getMaxUsagePerUser()){
            throw new AppException(ErrorCode.END_OF_USE);
        }

//        if (!isMinOrderAmount(order, discount)) {
//            throw new AppException(ErrorCode.DISCOUNT_NOT_VALUE);
//        }

//        double discountPrice = calculateDiscount(discount,order);

        order.setDiscount(discount);
//        order.setTotalPrice(discountPrice);

        DiscountUsage discountUsage = new DiscountUsage();
        discountUsage.setUser(user);
        discountUsage.setDiscount(discount);
        discountUsage.setUsedAt(LocalDateTime.now());
        discountUsageRepository.save(discountUsage);


        return discountMapper.toDiscountResponse(discount);
    }



//    Hàm tạo discount
    @PreAuthorize("hasRole('ADMIN')")
    public DiscountResponse createDiscount(DiscountRequest request){
        Discount discount = discountMapper.toDiscount(request);

        if (discount.getExpirationDate() == null) {
            discount.setExpirationDate(LocalDateTime.now().plusHours(1));
        }
        String code = generateDiscountCode(discount);
        discount.setCode(code);
        discount.setMaxUsagePerUser(1);
        return discountMapper.toDiscountResponse(discountRepository.save(discount));
    }

//    Get all discount
    public List<DiscountResponse> getAllDiscount(){
        return discountRepository.findAll().stream().map(discount -> discountMapper.toDiscountResponse(discount)).toList();
    }

//    Update Discound
    @PreAuthorize("hasRole('ADMIN')")
    public DiscountResponse updateDiscount(Long id, DiscountRequest request){
        Discount discount = discountRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DISCOUNT_NOT_EXISTED));

        DiscountScope oldScope = discount.getDiscountScope();
        DiscountType oldType = discount.getDiscountType();

        discountMapper.toUpdateDiscount(discount, request);

        discount.setExpirationDate(LocalDateTime.now().plusHours(1));

        if (!oldScope.equals(discount.getDiscountScope())) {
            discount.setCode(generateDiscountCode(discount));
        }

        if (!oldType.equals(discount.getDiscountType())) {
            discount.setCode(generateDiscountCode(discount));
        }

        return discountMapper.toDiscountResponse(discountRepository.save(discount));
    }

//    Delete Discount
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteDiscount(Long id){
        if(!discountRepository.existsById(id)){
            throw new AppException(ErrorCode.DISCOUNT_NOT_EXISTED);
        }
        discountRepository.deleteById(id);
    }

////    Hàm kiểm tra xem tiên order hoặc sản phẩmcosos thỏa điều kiện không
//    private boolean isMinOrderAmount( Orders order, Discount discount) {
//        if (discount.getDiscountScope() == DiscountScope.ORDER) {
//            return order.getTotalPrice() >= discount.getMinOrderAmount();
//        } else if (discount.getDiscountScope() == DiscountScope.PRODUCT) {
//            return order.getOrderDetails().stream()
//                    .anyMatch(detail -> detail.getProduct().getPrice() >=  discount.getMinOrderAmount());
//        }
//        return false;
//    }
//
//    //    Hàm tính tiền khi có mã giảm giá
//    private double calculateDiscount(Discount discount, Orders orders){
//        double discountPrice = 0;
//
//        if(discount.getDiscountScope() == DiscountScope.ORDER){
//            double totalPrice = orders.getTotalPrice();
//
//            if(discount.getDiscountType() == DiscountType.PERCENTAGE){
//                discountPrice = totalPrice - (totalPrice * discount.getDiscountValue() / 100);
//
//            }else{
//                discountPrice = totalPrice - discount.getDiscountValue()*1000;
//            }
//            orders.setTotalPrice(discountPrice);
//        }else if(discount.getDiscountScope() == DiscountScope.PRODUCT){
//            discountPrice = orders.getOrderDetails().stream().mapToDouble(
//                    detail -> {
//                        double productPrice = detail.getProduct().getPrice();
//                        double discountedPrice = productPrice;
//
//                        if (productPrice >= discount.getMinOrderAmount()) {
//                            if (discount.getDiscountType() == DiscountType.PERCENTAGE) {
//                                discountedPrice = productPrice - (productPrice * discount.getDiscountValue() / 100);
//                            } else {
//                                discountedPrice = productPrice - discount.getDiscountValue()*1000;
//                            }
//                        }
//
//                        detail.setTotalPrice(discountedPrice * detail.getQuantity());
//                        return detail.getTotalPrice();
//                    }).sum();
//            orders.setTotalPrice(discountPrice);
//        }
//        return discountPrice;
//    }

}
