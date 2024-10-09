package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.UserCreationRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.UserUpdateRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.UserResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    Chuyen doi doi tuong UserCreationRequest thanh User
    User toUser(UserCreationRequest request);

//    @MappingTarget chỉ định đối tượng đích (User) sẽ được cập nhật trực tiếp thay vì tạo mới.
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

//    Chuyen doi tuong User thanh UserResponse
    UserResponse toUserResponse(User user);

}
