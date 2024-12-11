package com.dacn.WebsiteBanDoCongNghe.service;

import com.dacn.WebsiteBanDoCongNghe.exception.AppException;
import com.dacn.WebsiteBanDoCongNghe.exception.ErrorCode;
import lombok.AccessLevel;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
// class xử lý lưu ảnh
public class FileStorageService {
    @Value("${file.upload-dir}")
    String uploadDir;

    public String saveFile(MultipartFile file) throws IOException {
        // Tạo tên file mới
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + File.separator + fileName);

        // Tạo thư mục nếu chưa tồn tại
        Files.createDirectories(filePath.getParent());

        // Lưu file vào hệ thống
        Files.write(filePath, file.getBytes());

        // Trả về đường dẫn URL tuyệt đối
        return createFileUrl(fileName);
    }

    private String createFileUrl(String fileName) {
        // Lấy url cơ sở của ứng dụng
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/") // Đường dẫn đến thư mục chứa ảnh
                .path(fileName)
                .toUriString();
    }

}
