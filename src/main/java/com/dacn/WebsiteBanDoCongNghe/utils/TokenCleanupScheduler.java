package com.dacn.WebsiteBanDoCongNghe.utils;

import com.dacn.WebsiteBanDoCongNghe.reponsitory.InvalidatedTokenReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// Xóa các token hết hạn tron db invalited_token
public class TokenCleanupScheduler {
    InvalidatedTokenReponsitory invalidatedTokenReponsitory;
    @Scheduled(fixedRate = 43200000) // 12h
    @Transactional
    public void cleanupExpiredTokens() {
        invalidatedTokenReponsitory.deleteByExpiryTimeBefore(LocalDateTime.now());
        System.out.println("Đã xóa các token đã hết hạn.");
    }
}
