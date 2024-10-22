package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.UserCreationRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.UserUpdateRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.UserResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    Chuyen doi doi tuong UserCreationRequest thanh User
    User toUser(UserCreationRequest request);

//    @MappingTarget chỉ định đối tượng đích (User) sẽ được cập nhật trực tiếp thay vì tạo mới.
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "firstname", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "lastname", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dob", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "gender", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "address", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "numberPhone", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

//    Chuyen doi tuong User thanh UserResponse
    UserResponse toUserResponse(User user);

}
