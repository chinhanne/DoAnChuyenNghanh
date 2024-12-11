package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.dto.request.CategoryRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CategoryResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ProductResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Category;
import com.dacn.WebsiteBanDoCongNghe.entity.Product;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.mapper.CategoryMapper;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.CategoryReponsitory;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.ProductReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CategoryService {
    CategoryReponsitory categoryReponsitory;
    CategoryMapper categoryMapper;
    FileStorageService fileStorageService;
    ProductReponsitory productReponsitory;

//    Created category
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse createdCategory(CategoryRequest request){
        Category category = categoryMapper.toCategory(request);
        try{
            categoryReponsitory.save(category);
        }catch(DataIntegrityViolationException e){
            throw new AppException(ErrorCode.NAME_CATEGORY_EXISTED);
        }
        return categoryMapper.toCategoryResponse(category);
    }

//    Updated category
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse updatedCategory(Long id, CategoryRequest request){
        Category category = categoryReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        categoryMapper.updateCategory(category, request);

        return categoryMapper.toCategoryResponse(categoryReponsitory.save(category));
    }

//    GetAll category
    public List<CategoryResponse> getAllCategory(){
        return categoryReponsitory.findAll().stream().map(categoryMapper::toCategoryResponse).toList();

    }

//    Delete category
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(Long id){
        if(!categoryReponsitory.existsById(id)){
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        categoryReponsitory.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deletedSoftCategory(Long categoryId){
        Category category = categoryReponsitory.findById(categoryId).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        category.setIsDelete(true);
        categoryReponsitory.save(category);

        List<Product> products = productReponsitory.findByCategoryIdAndIsDeleteFalse(categoryId);
        for(Product product : products){
            product.setIsCategoryVisible(false);
            productReponsitory.save(product);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void restoreCategory(Long categoryId){
        Category category = categoryReponsitory.findById(categoryId).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        category.setIsDelete(false);
        categoryReponsitory.save(category);

        List<Product> products = productReponsitory.findByCategoryIdAndIsDeleteFalse(categoryId);
        for(Product product : products){
            product.setIsCategoryVisible(true);
            productReponsitory.save(product);
        }
    }

//    Get all category has isDelete
    public List<CategoryResponse> getAllCategoryForIsDelete() {
        return categoryReponsitory.findAll().stream()
                .filter(category -> category.getIsDelete())
                .map(categoryMapper::toCategoryResponse).toList();
    }
}
