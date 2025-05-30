package com.example.application.controller.admin;

import com.example.application.exception.InternalServerException;
import com.example.application.exception.NotFoundException;
import com.example.application.entity.Image;
import com.example.application.exception.BadRequestException;
import com.example.application.security.CustomUserDetails;
import com.example.application.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
public class ImageController {

    private static final String UPLOAD_DIR = System.getProperty("user.home") + "/media/upload";

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
            "png", "jpg", "jpeg", "gif", "svg", "webp", "avif"
    );

    @Autowired
    private ImageService imageService;

    @PostMapping("/api/upload/files")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        // Tạo thư mục nếu chưa tồn tại
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            throw new BadRequestException("Tên file không hợp lệ!");
        }

        // Lấy phần mở rộng file
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

        // Kiểm tra định dạng file
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new BadRequestException("Không hỗ trợ định dạng file này!");
        }

        try {
            Image image = new Image();
            image.setId(UUID.randomUUID().toString());
            image.setName(file.getOriginalFilename());
            image.setSize(file.getSize());
            image.setType(extension);
            String link = "/media/static/" + image.getId() + "." + extension;
            image.setLink(link);
            image.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            image.setCreatedBy(((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());

            // Ghi file vào thư mục
            File serveFile = new File(UPLOAD_DIR + "/" + image.getId() + "." + extension);
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(serveFile))) {
                bos.write(file.getBytes());
            }

            imageService.saveImage(image);
            return ResponseEntity.ok(link);

        } catch (Exception e) {
            throw new InternalServerException("Có lỗi trong quá trình upload file!");
        }
    }

    @GetMapping("/media/static/{filename:.+}")
    public ResponseEntity<Object> downloadFile(@PathVariable String filename) {
        File file = new File(UPLOAD_DIR + "/" + filename);
        if (!file.exists()) {
            throw new NotFoundException("File không tồn tại!");
        }

        try {
            UrlResource resource = new UrlResource(file.toURI());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (MalformedURLException ex) {
            throw new NotFoundException("File không tồn tại!");
        }
    }

    @DeleteMapping("/api/delete-image/{filename:.+}")
    public ResponseEntity<Object> deleteImage(@PathVariable String filename) {
        imageService.deleteImage(UPLOAD_DIR, filename);
        return ResponseEntity.ok("Xóa file thành công!");
    }
}
