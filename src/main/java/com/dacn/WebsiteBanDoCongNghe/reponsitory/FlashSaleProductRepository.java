package com.dacn.WebsiteBanDoCongNghe.reponsitory;

import com.dacn.WebsiteBanDoCongNghe.entity.FlashSaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashSaleProductRepository extends JpaRepository<FlashSaleProduct,Long> {
}
