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
    Comment toComment(CommentRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toUpdateComment(@MappingTarget Comment comment, CommentRequest request);

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "childComments", target = "childComments")  // Đảm bảo ánh xạ đúng childComments
    CommentResponse toCommentResponse(Comment comment);

    @Mapping(source = "user.username", target = "userName")
    @Mapping(target = "parentId", source = "parentComment.id")  // Ánh xạ parentId cho child comment
    ChildCommentResponse toChildCommentResponse(Comment comment);

    List<ChildCommentResponse> toChildCommentResponseList(List<Comment> childComments);
}
