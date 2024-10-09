package com.dacn.WebsiteBanDoCongNghe.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Lỗi không xác định",HttpStatus.INTERNAL_SERVER_ERROR), // 500
    INVALID_KEY(1001,"Thông tin khóa bị sai",HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002,"Người dùng đã tồn tại",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1003,"Người dùng không tồn tại",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1004,"Tên người dùng phải ít nhất {min} ký tự",HttpStatus.BAD_REQUEST),
    USERNAME_NOT_NULL(1005,"Tên người dùng không được bỏ trống",HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1006,"Mật khẩu phải ít nhất {min} ký tự",HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_NULL(1007,"Mật khẩu không được bỏ trống",HttpStatus.BAD_REQUEST),
    EMAIL_NOT_NULL(1008,"Email không được bỏ trống",HttpStatus.BAD_REQUEST),
    EMAIL_INVALID_FORMAT(1009,"Email sai định dạng",HttpStatus.BAD_REQUEST), // 400
    UNAUTHENTICATED(1010, "Lỗi không có quyền xác thực",HttpStatus.UNAUTHORIZED), // 401
    UNAUTHORIZED(1011, "Lỗi không được phép truy cập",HttpStatus.FORBIDDEN), // 403
    ROLES_NOT_EXISTED(1012,"Roles không tồn tại",HttpStatus.BAD_REQUEST),
    INVALID_DOB(1013,"Người dùng phải lớn hơn {min} tuổi",HttpStatus.BAD_REQUEST)
    ;

    int code;
    String message;
    HttpStatus httpStatus;
}
