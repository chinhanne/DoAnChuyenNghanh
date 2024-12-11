package com.dacn.WebsiteBanDoCongNghe.mapper;

import com.dacn.WebsiteBanDoCongNghe.dto.request.CommentRequest;
import com.dacn.WebsiteBanDoCongNghe.dto.response.ChildCommentResponse;
import com.dacn.WebsiteBanDoCongNghe.dto.response.CommentResponse;
import com.dacn.WebsiteBanDoCongNghe.entity.Comment;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "parentCommentId", target = "parentComment.id", ignore = true)
    Comment toComment(CommentRequest request);

    //    Chỉ cập nhật các giá trị không null còn các giá trị null thì giữ nguyên không cập nhật
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdateComment(@MappingTarget Comment comment, CommentRequest request);
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "childComments", target = "childComments")
    @Mapping(source = "parentComment.id", target = "parentId")

    CommentResponse toCommentResponse(Comment comment);

    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "parentComment.id", target = "parentId")
    ChildCommentResponse toChildCommentResponse(Comment comment);

    // Định nghĩa ánh xạ danh sách comment con
    List<ChildCommentResponse> toChildCommentResponseList(List<Comment> childComments);
}
