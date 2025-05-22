package org.sale.project.service;

import jakarta.servlet.ServletContext;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UploadService {
    ServletContext servletContext;

    // Danh sách định dạng file ảnh cho phép
    static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "image/png", "image/jpeg", "image/jpg"
    );

    // Giới hạn kích thước file: 5MB
    static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    public String uploadImage(MultipartFile file, String path) {
        if (file.isEmpty()) return "";

        try {
            // 1. Kiểm tra MIME type thực tế
            String contentType = file.getContentType();
            if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
                throw new IllegalArgumentException("Unsupported file type: " + contentType);
            }

            // 2. Kiểm tra kích thước file
            if (file.getSize() > MAX_FILE_SIZE) {
                throw new IllegalArgumentException("File is too large (max 5MB).");
            }

            // 3. Loại bỏ ký tự đặc biệt trong tên file
            String originalName = Paths.get(file.getOriginalFilename()).getFileName().toString();
            String sanitizedFilename = originalName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");

            // 4. Đường dẫn lưu file
            String rootPath = this.servletContext.getRealPath("/resources/images");
            File dir = new File(rootPath + File.separator + path);
            if (!dir.exists()) dir.mkdirs();

            String finalName = System.currentTimeMillis() + "-" + sanitizedFilename;
            File serverFile = new File(dir, finalName);

            // 5. Ghi file
            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                stream.write(file.getBytes());
            }

            return finalName;
        } catch (Exception e) {
            e.printStackTrace(); // log chi tiết lỗi
            return "";
        }
    }

    public void updateNameFileProduct(String oldName, String newName) {
        String rootPath = this.servletContext.getRealPath("/resources/images/product");

        try {
            File oldFile = new File(rootPath + File.separator + oldName);
            File newFile = new File(rootPath + File.separator + newName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_"));

            if (oldFile.exists()) {
                boolean success = oldFile.renameTo(newFile);
                if (!success) {
                    throw new IOException("Rename failed.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
