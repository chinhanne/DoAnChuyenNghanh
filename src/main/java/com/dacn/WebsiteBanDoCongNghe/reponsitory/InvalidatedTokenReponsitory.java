package com.dacn.WebsiteBanDoCongNghe.reponsitory;

import com.dacn.WebsiteBanDoCongNghe.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface InvalidatedTokenReponsitory extends JpaRepository<InvalidatedToken, String> {
    void deleteByExpiryTimeBefore(LocalDateTime expiryTime);
}
