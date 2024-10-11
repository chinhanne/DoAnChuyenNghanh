package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.CategoryRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.UserUpdateRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CategoryResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Category;
import com.dacn.WebsiteBanDoCongNghe.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);
    void updateCategory(@MappingTarget Category category, CategoryRequest request);
}
