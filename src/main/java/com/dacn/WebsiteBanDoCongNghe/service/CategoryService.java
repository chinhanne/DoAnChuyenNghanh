package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.dto.request.CategoryRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CategoryResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Category;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.mapper.CategoryMapper;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.CategoryReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
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

//    Created category
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse createdCategory(CategoryRequest request, MultipartFile imageFile) throws IOException {
        Category category = categoryMapper.toCategory(request);
        if(imageFile != null && !imageFile.isEmpty()){
            String imageUrl = fileStorageService.saveFile(imageFile);
            category.setImage(imageUrl);
        }
        try{
            categoryReponsitory.save(category);
        }catch(DataIntegrityViolationException e){
            throw new AppException(ErrorCode.NAME_CATEGORY_EXISTED);
        }
        return categoryMapper.toCategoryResponse(category);
    }

//    Updated category
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse updatedCategory(Long id, CategoryRequest request, MultipartFile imageFile) throws IOException {
        Category category = categoryReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        categoryMapper.updateCategory(category, request);
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileStorageService.saveFile(imageFile);
            category.setImage(imageUrl);
        }
        return categoryMapper.toCategoryResponse(categoryReponsitory.save(category));
    }

//    GetAll category
//    @PreAuthorize("hasRole('ADMIN')")
    public List<CategoryResponse> getAllCategory(){
        return categoryReponsitory.findAll().stream().map(category -> categoryMapper.toCategoryResponse(category)).toList();
    }

//    Delete category
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(Long id){
        if(!categoryReponsitory.existsById(id)){
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        categoryReponsitory.deleteById(id);
    }
}
