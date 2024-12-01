package com.dacn.WebsiteBanDoCongNghe.reponsitory;

import com.dacn.WebsiteBanDoCongNghe.entity.Brand;
import com.dacn.WebsiteBanDoCongNghe.entity.Category;
import com.dacn.WebsiteBanDoCongNghe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReponsitory extends JpaRepository<Product,Long> {
    List<Product> findByNameAndCategory_Name(String name, String categoryName);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryIn(List<Category> categories);
    List<Product> findByBrandIn(List<Brand> brands);
    List<Product> findByBrandIdAndIsDeleteFalse(Long brandId);
    List<Product> findByCategoryIdAndIsDeleteFalse(Long categoryId);
}
