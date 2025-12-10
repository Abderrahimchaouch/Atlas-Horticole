package com.atlashorticole.product_service.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CloudinaryUtils {


        // Allowed extensions
    private static final List<String> ALLOWED_IMAGE_EXTENSIONS = Arrays.asList(
        ".jpg", ".jpeg", ".png", ".gif", ".webp", ".svg"
    );
    
    private static final List<String> ALLOWED_PDF_EXTENSIONS = Arrays.asList(
        ".pdf"
    );
    public static void validateFile(MultipartFile file, String fileType) {

        List<String> allowedExtensions = new ArrayList<>();
        allowedExtensions.addAll( fileType=="PDF"? ALLOWED_PDF_EXTENSIONS : ALLOWED_IMAGE_EXTENSIONS);


        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(fileType + " file is empty");
        }
        
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid file name");
        }
        
        // Check extension
        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        if (!allowedExtensions.contains(extension)) {
            throw new IllegalArgumentException(
                String.format("%s file type not supported. Allowed extensions: %s",
                    fileType, String.join(", ", allowedExtensions))
            );
        }
        
        // Check size (max 10MB)
        long maxSize = 10 * 1024 * 1024; // 10MB
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException(
                String.format("File too large. Maximum size: %d MB", maxSize / (1024 * 1024))
            );
        }
    }

    public static String extractPublicId(String url) {
        // Ex: https://res.cloudinary.com/cloudname/image/upload/v123/products/images/abc.jpg
        // Returns: products/images/abc
        
        if (url == null || !url.contains("cloudinary.com")) {
            return "unknown";
        }
        
        String[] parts = url.split("/");
        int uploadIndex = -1;
        
        for (int i = 0; i < parts.length; i++) {
            if ("upload".equals(parts[i])) {
                uploadIndex = i;
                break;
            }
        }
        
        if (uploadIndex == -1 || uploadIndex >= parts.length - 1) {
            return "unknown";
        }
        
        // Reconstruct publicId
        StringBuilder publicId = new StringBuilder();
        for (int i = uploadIndex + 2; i < parts.length; i++) {
            if (i > uploadIndex + 2) publicId.append("/");
            publicId.append(parts[i]);
        }
        
        // Remove extension
        String result = publicId.toString();
        int dotIndex = result.lastIndexOf(".");
        if (dotIndex != -1) {
            result = result.substring(0, dotIndex);
        }
        
        return result;
    }
    
}
