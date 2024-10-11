package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.dto.request.BrandRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.CategoryRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.BrandResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CategoryResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Brand;
import com.dacn.WebsiteBanDoCongNghe.entity.Category;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.mapper.BrandMapper;
import com.dacn.WebsiteBanDoCongNghe.mapper.CategoryMapper;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.BrandRepository;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.CategoryReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class BrandService {
    BrandRepository brandRepository;
    BrandMapper brandMapper;

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
    @PreAuthorize("hasRole('ADMIN')")
    public List<BrandResponse> getAllBrand(){
        return brandRepository.findAll().stream().map(brand -> brandMapper.toBrandResponse(brand)).toList();
    }

//    Delete category
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBrand(Long id){
        if(!brandRepository.existsById(id)){
            throw new AppException(ErrorCode.BRAND_NOT_EXISTED);
        }
        brandRepository.deleteById(id);
    }
}
