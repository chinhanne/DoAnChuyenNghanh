package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.request.UserCreationRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.UserInfoLoginGoogleCreateRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.UserUpdatePasswordRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.UserUpdateRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ProductResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.UserResponse;
import com.dacn.WebsiteBanDoCongNghe.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

//    Create User
    @PostMapping
    public ApiResponse<UserResponse> createUser(@ModelAttribute @Valid UserCreationRequest request,
                                                MultipartFile imageFile) throws IOException {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request,imageFile))
                .build();
    }

    @PostMapping("/create/userInfoLoginGoogle")
    public ApiResponse<Void> updateUserLoginGoogle(@RequestBody @Valid UserInfoLoginGoogleCreateRequest request){
        userService.updateUserInfoLoginGoogle(request);
        return ApiResponse.<Void>builder()
                .message("Đã cập nhật thông tin người dùng thành công")
                .build();
    }

    //    Get myInfo
    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

//    Get all user
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUser(){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUser())
                .build();
    }
//    Get User by id
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(id))
                .build();
    }

//    Get all user is delete soft
    @GetMapping("/list-user-delete-soft")
    public ApiResponse<List<UserResponse>> listUserSoft(){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUserIsDelete())
                .build();
    }

//    Update User
    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String id,
                                                @ModelAttribute @Valid UserUpdateRequest request, MultipartFile imageFile) throws IOException {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id,request,imageFile))
                .build();
    }

//    Update myInfo
    @PutMapping("/myInfo")
    public ApiResponse<UserResponse> updateMyInfo(@ModelAttribute @Valid UserUpdateRequest request, MultipartFile imageFile) throws IOException{
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateMyInfo(name,request,imageFile))
                .build();
    }

    //    Update password myInfo
    @PutMapping("/password-myInfo")
    public ApiResponse<UserResponse> updatePasswordMyInfo(@RequestBody @Valid UserUpdatePasswordRequest request){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return ApiResponse.<UserResponse>builder()
                .result(userService.updatePasswordMyInfo(name,request))
                .build();
    }

//    Restore user after delete soft
    @PutMapping("/restore/{id}")
    public ApiResponse<String> restoreProduct(@PathVariable String id) {
        userService.restoreUser(id);
        return ApiResponse.<String>builder()
                .result("Người dùng đã được khôi phục thành công")
                .build();
    }

//    Delete User
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .result("Người dùng đã được xóa")
                .build();
    }

//    Delete soft user
    @DeleteMapping("/soft-user/{id}")
    public ApiResponse<String> deleteUserSoft(@PathVariable String id) {
        userService.deleteSoftUser(id);
        return ApiResponse.<String>builder()
                .result("Người dùng đã được xóa thành công")
                .build();
    }
}
