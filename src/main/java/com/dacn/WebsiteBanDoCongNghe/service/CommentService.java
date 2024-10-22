package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.dto.request.CommentRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.request.UpdateCommentDisplayRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ChildCommentResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CommentResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Comment;
import com.dacn.WebsiteBanDoCongNghe.entity.Product;
import com.dacn.WebsiteBanDoCongNghe.entity.User;
import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import com.dacn.WebsiteBanDoCongNghe.mapper.CommentMapper;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.CommentReponsitory;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.ProductReponsitory;
import com.dacn.WebsiteBanDoCongNghe.reponsitory.UserReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CommentService {
   CommentReponsitory commentReponsitory;
   CommentMapper commentMapper;
   UserReponsitory userReponsitory;
   ProductReponsitory productReponsitory;

//   Create comment
    @PreAuthorize("isAuthenticated()")
    public CommentResponse createComment(CommentRequest request){

        Product product = productReponsitory.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_EXISTED));

        User user = userReponsitory.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Comment comment = commentMapper.toComment(request);
        comment.setProduct(product);
        comment.setUser(user);

//        Kiểm tra comment có parentComment khong
        if(request.getParentCommentId() != null){
            Comment parentComment = commentReponsitory.findById(request.getParentCommentId())
                    .orElseThrow(() -> new AppException(ErrorCode.PARENT_COMMENT_NOT_FOUND));
            comment.setParentComment(parentComment);
        }else{
            comment.setParentComment(null);
        }
        commentReponsitory.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

//    Lấy comment theo product
    public List<CommentResponse> getCommentByProduct(Long productId){
        List<Comment> comments = commentReponsitory.findByProductId(productId);
        return comments.stream().map(comment -> commentMapper.toCommentResponse(comment)).collect(Collectors.toList());
    }

//    Update Comment
    @PreAuthorize("hasRole('ADMIN') or @commentService.isCommentOwner(#id, authentication)")
    public CommentResponse updateComment(Long id, CommentRequest request, Authentication authentication) {
        Comment comment = commentReponsitory.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_EXISTED));

        // Lấy thông tin người dùng từ JWT token
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userNameFromToken = jwt.getSubject();

        // Kiểm tra xem người dùng có phải là người đã tạo comment hay không
        if (!comment.getUser().getUsername().equals(userNameFromToken) && !authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Cập nhật comment
        commentMapper.toUpdateComment(comment, request);
        commentReponsitory.save(comment);
        return commentMapper.toCommentResponse(comment);
    }


    //    Get comment by id comment
    public CommentResponse getCommentById(Long id){
        return commentMapper.toCommentResponse(commentReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_EXISTED)));
    }

//    ADMIN Update display
    @PreAuthorize("hasRole('ADMIN')")
    public CommentResponse updateDisplay(Long id, UpdateCommentDisplayRequest updateCommentDisplayRequest){
        Comment comment = commentReponsitory.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_EXISTED));

        comment.setDisplay(updateCommentDisplayRequest.isDisplay());
        commentReponsitory.save(comment);
        return commentMapper.toCommentResponse(comment);
    }

//    Xóa comment
    @PreAuthorize("hasRole('ADMIN') or @commentService.isCommentOwner(#id, authentication)")
    public void deleteComment(Long id, Authentication authentication) {
        Comment comment = commentReponsitory.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_EXISTED));

        // Lấy thông tin người dùng từ JWT token
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userNameFromToken = jwt.getSubject();

        // Kiểm tra xem người dùng có phải là người đã tạo comment hay không
        if (!comment.getUser().getUsername().equals(userNameFromToken) && !authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Xóa comment
        commentReponsitory.delete(comment);
    }


    //     Lấy comment cha và các comment con của nó
    public CommentResponse getCommentWithChildren(Long id) {
        Comment comment = commentReponsitory.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_EXISTED));

        CommentResponse commentResponse = commentMapper.toCommentResponse(comment);

        List<ChildCommentResponse> childComment = commentMapper.toChildCommentResponseList(comment.getChildComments());
        commentResponse.setUserName(comment.getUser().getUsername());
        commentResponse.setChildComments(childComment);
        return commentResponse;
    }

//    Kiểm tra xem người dùng có phải là chủ sở hữu của comment không
    public boolean isCommentOwner(Long commentId, Authentication authentication) {
        Comment comment = commentReponsitory.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_EXISTED));

        Jwt jwt = (Jwt) authentication.getPrincipal();
        String userNameFromToken = jwt.getSubject();


        return comment.getUser().getUsername().equals(userNameFromToken); // So sánh username
    }
}
