package com.dacn.WebsiteBanDoCongNghe.reponsitory;

import com.dacn.WebsiteBanDoCongNghe.entity.FlashSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashSaleRepository extends JpaRepository<FlashSale,Long> {
}
