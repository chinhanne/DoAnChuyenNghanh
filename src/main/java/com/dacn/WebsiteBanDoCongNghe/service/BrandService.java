package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.dto.request.BrandRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.BrandResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CategoryResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Brand;
import com.dacn.WebsiteBanDoCongNghe.entity.Category;
import com.dacn.WebsiteBanDoCongNghe.entity.Product;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.mapper.BrandMapper;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.BrandRepository;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.ProductReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;
    ProductReponsitory productReponsitory;

//    Created brand
    @PreAuthorize("hasRole('ADMIN')")
    public BrandResponse createdBrand(BrandRequest request){
        Brand brand = brandMapper.toBrand(request);
        try{
            brandRepository.save(brand);
        }catch(DataIntegrityViolationException e){
            throw new AppException(ErrorCode.NAME_BRAND_EXISTED);
        }
        return brandMapper.toBrandResponse(brand);
    }

//    Updated brand
    @PreAuthorize("hasRole('ADMIN')")
    public BrandResponse updatedBrand(Long id, BrandRequest request){
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        brandMapper.updateBrand(brand, request);
        return brandMapper.toBrandResponse(brandRepository.save(brand));
    }

//    GetAll category
    public List<BrandResponse> getAllBrand(){
        return brandRepository.findAll().stream().map(brandMapper::toBrandResponse).toList();
    }

//    Delete category
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBrand(Long id){
        if(!brandRepository.existsById(id)){
            throw new AppException(ErrorCode.BRAND_NOT_EXISTED);
        }
        brandRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deletedSoftBrand(Long brandId){
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        brand.setIsDelete(true);
        brandRepository.save(brand);

        List<Product> products = productReponsitory.findByBrandIdAndIsDeleteFalse(brandId);
        for(Product product : products){
            product.setIsBrandVisible(false);
            productReponsitory.save(product);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void restoreBrand(Long brandId){
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new AppException(ErrorCode.BRAND_NOT_EXISTED));
        brand.setIsDelete(false);
        brandRepository.save(brand);

        List<Product> products = productReponsitory.findByBrandIdAndIsDeleteFalse(brandId);
        for(Product product : products){
            product.setIsBrandVisible(true);
            productReponsitory.save(product);
        }
    }

    public List<BrandResponse> getAllBrandForIsDelete() {
        return brandRepository.findAll().stream()
                .filter(brand -> brand.getIsDelete())
                .map(brandMapper::toBrandResponse).toList();
    }
}
