package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.request.UserCreationRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.UserUpdateRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.UserResponse;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
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
                                                @RequestParam MultipartFile imageFile) throws IOException {
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request,imageFile))
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

//    Update User
    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String id,
                                                @ModelAttribute @Valid UserUpdateRequest request,
                                                @RequestParam(required = false) MultipartFile image) throws IOException {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(id,request,image))
                .build();
    }

//    Update myInfo
    @PutMapping("/myInfo")
    public ApiResponse<UserResponse> updateMyInfo(@ModelAttribute @Valid UserUpdateRequest request,
                                                  @RequestParam(required = false) MultipartFile image) throws IOException{
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateMyInfo(name,request,image))
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
}
