package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.dto.request.FlashSaleProductRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.FlashSaleRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.FlashSaleResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.FlashSale;
import com.dacn.WebsiteBanDoCongNghe.entity.FlashSaleProduct;
import com.dacn.WebsiteBanDoCongNghe.entity.Product;
import com.dacn.WebsiteBanDoCongNghe.enums.FlashSaleStatus;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.mapper.FlashSaleMapper;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.FlashSaleProductRepository;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.FlashSaleRepository;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.ProductReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class FlashSaleService {
    FlashSaleMapper flashSaleMapper;
    FlashSaleProductRepository flashSaleProductRepository;
    FlashSaleRepository flashSaleRepository;
    ProductReponsitory productReponsitory;

//    Create Flash Sale
    public FlashSaleResponse createFlashSale(FlashSaleRequest request) {
        FlashSale flashSale = flashSaleMapper.toFlashSale(request);
        flashSale.setStatus(FlashSaleStatus.UPCOMING);

        List<FlashSaleProduct> flashSaleProducts = request.getFlashSaleProducts().stream()
            .map(flashSaleProductRequest -> {
                Product product = productReponsitory.findById(flashSaleProductRequest.getProductId())
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

                double priceSale = product.getPrice() - (product.getPrice() * (request.getDiscountPercentage() / 100.0));

                FlashSaleProduct flashSaleProduct = flashSaleMapper.toFlashSaleProduct(flashSaleProductRequest);
                flashSaleProduct.setProduct(product);
                flashSaleProduct.setFlashSale(flashSale);
                flashSaleProduct.setPriceSale(priceSale);
                return flashSaleProduct;
            }).collect(Collectors.toList());

        flashSale.setFlashSaleProducts(flashSaleProducts);

        return flashSaleMapper.toResponseFlashSale(flashSaleRepository.save(flashSale));
    }


    public List<FlashSaleResponse> getAllFlashSale(){
        return flashSaleRepository.findAll().stream().map(flashSaleMapper::toResponseFlashSale).toList();
    }

    public FlashSaleResponse getFlashSaleById(Long id){
        return flashSaleMapper.toResponseFlashSale(flashSaleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED)));
    }

    public FlashSaleResponse updateFlashSale(Long id, FlashSaleRequest request){
        FlashSale flashSale = flashSaleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED));
        flashSale.setStartTimeSale(request.getStartTimeSale());
        flashSale.setEndTimeSale(request.getEndTimeSale());
        flashSale.setDiscountPercentage(request.getDiscountPercentage());

        List<FlashSaleProduct> currentFlashSaleProduct = flashSale.getFlashSaleProducts();

        for(FlashSaleProductRequest productRequest : request.getFlashSaleProducts()){
            Product product = productReponsitory.findById(productRequest.getProductId()).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

            double priceSale = product.getPrice() - (product.getPrice() * request.getDiscountPercentage() / 100.0);

            FlashSaleProduct existingProduct = currentFlashSaleProduct.stream()
                    .filter(p -> p.getProduct().getId().equals(productRequest.getProductId()))
                    .findFirst()
                    .orElse(null);

            if(existingProduct != null){
                existingProduct.setPriceSale(priceSale);
            }else{
                FlashSaleProduct newProduct = flashSaleMapper.toFlashSaleProduct(productRequest);
                newProduct.setFlashSale(flashSale);
                newProduct.setPriceSale(priceSale);
                currentFlashSaleProduct.add(newProduct);
            }
        }
        // Xóa sản phẩm không còn trong request
        currentFlashSaleProduct.removeIf(p -> request.getFlashSaleProducts().stream()
                .noneMatch(r -> r.getProductId().equals(p.getProduct().getId())));

        flashSaleRepository.save(flashSale);
        return flashSaleMapper.toResponseFlashSale(flashSale);
    }

    public void deleteFlashSale(Long id){
        if(!flashSaleRepository.existsById(id)){
            throw new AppException(ErrorCode.FLASH_SALE_NOT_EXISTED);
        }
        flashSaleRepository.deleteById(id);
    }

}

