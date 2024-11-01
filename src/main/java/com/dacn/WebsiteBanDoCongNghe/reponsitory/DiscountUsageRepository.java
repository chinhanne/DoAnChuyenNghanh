package com.dacn.WebsiteBanDoCongNghe.reponsitory;

import com.dacn.WebsiteBanDoCongNghe.entity.Discount;
import com.dacn.WebsiteBanDoCongNghe.entity.DiscountUsage;
import com.dacn.WebsiteBanDoCongNghe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountUsageRepository extends JpaRepository<DiscountUsage, Long> {
   long countByDiscountAndUser(Discount discount, User user);
}
