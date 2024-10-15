package com.dacn.WebsiteBanDoCongNghe.controller;

import com.dacn.WebsiteBanDoCongNghe.dto.request.CommentRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.UpdateCommentDisplayRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CommentResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Comment;
import com.dacn.WebsiteBanDoCongNghe.entity.User;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.service.CommentService;
import com.dacn.WebsiteBanDoCongNghe.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CommentController {
    CommentService commentService;
    UserService userService;

//    Create comment
    @PostMapping
    public ApiResponse<CommentResponse> created(@RequestBody @Valid CommentRequest request){
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.createComment(request))
                .build();
    }

//    Update comment
    @PutMapping("/{id}")
    public ApiResponse<CommentResponse> updated(@PathVariable Long id, @RequestBody @Valid CommentRequest request, Authentication authentication) {
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.updateComment(id, request, authentication))
                .build();
    }

//    Admin update display comment
    @PutMapping("/display/{id}")
    public ApiResponse<CommentResponse> updateDisplayComment(@PathVariable Long id, @RequestBody UpdateCommentDisplayRequest request){
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.updateDisplay(id,request))
                .build();
    }

//    Delete Comment
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleted(@PathVariable Long id,Authentication authentication){

        commentService.deleteComment(id, authentication);
        return ApiResponse.<String>builder()
                .result("Comment đã được xóa")
                .build();
    }

//    Get comment by product
    @GetMapping("/product/{productId}")
    public ApiResponse<List<CommentResponse>> getCommentByProduct(@PathVariable Long productId){
        return ApiResponse.<List<CommentResponse>>builder()
                .result(commentService.getCommentByProduct(productId))
                .build();
    }


//    Get comment by id comment
    @GetMapping("/{id}")
    public ApiResponse<CommentResponse> getCommentById(@PathVariable Long id){
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.getCommentById(id))
                .build();
    }

//    Get parent comment and child comment
    @GetMapping("/with-child/{id}")
    public ApiResponse<CommentResponse> getParentCommentAndChildComment(@PathVariable Long id){
        return ApiResponse.<CommentResponse>builder()
                .result(commentService.getCommentWithChildren(id))
                .build();
    }
}
