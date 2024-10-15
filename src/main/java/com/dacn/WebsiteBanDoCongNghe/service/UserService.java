package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.dto.request.UserCreationRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.UserUpdateRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.UserResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.User;
import com.dacn.WebsiteBanDoCongNghe.entity.Role;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.mapper.UserMapper;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.RoleReponsitory;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.UserReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserReponsitory userReponsitory;

    UserMapper userMapper;
    RoleReponsitory roleReponsitory;

    PasswordEncoder passwordEncoder;

//    Create User
    public UserResponse createUser(UserCreationRequest request){
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();
        roleReponsitory.findById("USER").ifPresent(roles::add);
        user.setRoles(roles);

        try{
            user = userReponsitory.save(user);
        }catch(DataIntegrityViolationException e){
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Chuyển đối tượng User thành UserResponse
        return userMapper.toUserResponse(user);
    }



    //    Get myInfo
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getMyInfo(){
        var name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userReponsitory.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

//    Get all user
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUser(){
        return userReponsitory.findAll().stream().map(userMapper::toUserResponse).toList();
    }


//    Get user by id
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserById(String id){
        return userMapper.toUserResponse(userReponsitory.findById(id).orElseThrow(() -> new  AppException(ErrorCode.USER_NOT_EXISTED)));
    }

//    Update user
    @PostAuthorize("hasRole('ADMIN')")
    public UserResponse updateUser(String id, UserUpdateRequest request){
        User user = userReponsitory.findById(id).orElseThrow(() -> new  AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        var roles = roleReponsitory.findAllById(request.getRoles());

//      Kiểm tra xem roles truyền vào từ request có hợp lệ không
        if (roles.size() != request.getRoles().size()) {
            throw new AppException(ErrorCode.ROLES_NOT_EXISTED);
        }
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userReponsitory.save(user));
    }

//    Update myInfo
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateMyInfo(String username, UserUpdateRequest request) {
        User user = userReponsitory.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user,request); // Cập nhật thông tin người dùng
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toUserResponse(userReponsitory.save(user)); // Lưu và trả về
    }

//    Delete user
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String id){
        userReponsitory.deleteById(id);
    }

}
