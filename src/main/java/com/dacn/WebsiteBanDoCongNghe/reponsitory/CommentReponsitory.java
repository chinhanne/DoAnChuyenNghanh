package com.dacn.WebsiteBanDoCongNghe.reponsitory;

import com.dacn.WebsiteBanDoCongNghe.entity.Category;
import com.dacn.WebsiteBanDoCongNghe.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentReponsitory extends JpaRepository<Comment,Long> {
    List<Comment> findByProductId(Long productId);
}
