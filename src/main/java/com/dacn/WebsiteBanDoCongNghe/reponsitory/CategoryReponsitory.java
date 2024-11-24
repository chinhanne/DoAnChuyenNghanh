package com.dacn.WebsiteBanDoCongNghe.reponsitory;

import com.dacn.WebsiteBanDoCongNghe.entity.Category;
import com.dacn.WebsiteBanDoCongNghe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryReponsitory extends JpaRepository<Category,Long> {
    List<Category> findByNameContainingIgnoreCase(String name);
}
