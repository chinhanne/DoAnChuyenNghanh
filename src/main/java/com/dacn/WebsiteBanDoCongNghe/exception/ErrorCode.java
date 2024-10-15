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
    INVALID_DOB(1013,"Người dùng phải lớn hơn {min} tuổi",HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED(1014,"Thể loại không tồn tại",HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXISTED(1015,"Sản phẩm không tồn tại",HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_NOT_NULL(1016,"Tên thể loại không được bỏ trống", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_NOT_NULL(1017,"Tên sản phẩm không được bỏ trống", HttpStatus.BAD_REQUEST),
    ADDRESS_NOT_NULL(1018,"Địa chỉ không được bỏ trống", HttpStatus.BAD_REQUEST),
    NUMBERPHONE_NOT_NULL(1019,"Số điện thoại không được bỏ trống", HttpStatus.BAD_REQUEST),
    NAME_CATEGORY_EXISTED(1020,"Thể loại đã tồn tại", HttpStatus.BAD_REQUEST),
    PRICE_PRODUCT_NOT_NULL(1021,"Giá sản phẩm không được bỏ trống và phải lớn hơn 0", HttpStatus.BAD_REQUEST),
    MIN_GENDER(1022,"vGiá trị gender phải là 0 hoặc 1", HttpStatus.BAD_REQUEST),
    MAX_GENDER(1023,"Giá trị gender phải là 0 hoặc 1", HttpStatus.BAD_REQUEST),
    BRAND_NAME_NOT_NULL(1024,"Tên hãng không được bỏ trống", HttpStatus.BAD_REQUEST),
    BRAND_NOT_EXISTED(1025,"Hãng không tồn tại",HttpStatus.BAD_REQUEST),
    NAME_BRAND_EXISTED(1026,"Hãng đã tồn tại", HttpStatus.BAD_REQUEST),
    PRODUCT_ID_NOT_NULL(1027,"Id sản phẩm không được bỏ trống", HttpStatus.BAD_REQUEST),
    USER_ID_NOT_NULL(1028,"Id người dùng không được bỏ trống", HttpStatus.BAD_REQUEST),
    CONTENT_COMMENT_NOT_NULL(1029,"Nội dung comment không được bỏ trống", HttpStatus.BAD_REQUEST),
    PARENT_COMMENT_NOT_FOUND(1030,"Comment cha không tồn tại", HttpStatus.BAD_REQUEST),
    COMMENT_NOT_EXISTED(1031,"Bình luận không tồn tại",HttpStatus.BAD_REQUEST),

    ;

    int code;
    String message;
    HttpStatus httpStatus;
}
