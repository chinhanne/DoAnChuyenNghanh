package com.dacn.WebsiteBanDoCongNghe.configuration;

import com.dacn.WebsiteBanDoCongNghe.dto.response.ApiResponse;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        Lấy ra lỗi
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
//        Lấy ra httpStatus của lỗi
        response.setStatus(errorCode.getHttpStatus().value());

//        set content-type cho response là json
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

//        Xây dựng 1 đối tượng ApiResponse để trả về mã code và message
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();

//        Chuyển đổi đối tượng ApiResponse thành String
        ObjectMapper objectMapper = new ObjectMapper();

//        Viết nội dung trả về dưới dạng chuỗi cho client
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();
    }
}
