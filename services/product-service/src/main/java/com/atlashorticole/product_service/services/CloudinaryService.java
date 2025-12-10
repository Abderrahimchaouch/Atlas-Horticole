package com.atlashorticole.product_service.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CloudinaryService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) {
        try {
            log.info("Upload image: {}", file.getOriginalFilename());
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "agricole/products/images",
                            "ressource_type", "image"));
            String url = (String) uploadResult.get("secure_url");
            log.info("Image uploaded: {}", url);
            return url;
        } catch (IOException e) {
            log.error("Upload failed", e);
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }

    public String uploadPdf(MultipartFile file) {
        try {
            log.info("Uploading PDF: {}", file.getOriginalFilename());

            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "agricole/products/documents",
                            "resource_type", "raw"));

            String url = (String) uploadResult.get("secure_url");
            log.info("PDF uploaded: {}", url);
            return url;

        } catch (IOException e) {
            log.error("Upload failed", e);
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }

    public boolean deleteFile(String publicId, String resourceType) {
        try {
            log.info("Deleting file from Cloudinary - publicId: {}, resourceType: {}",
                    publicId, resourceType);
            Map<?, ?> destroyResult = cloudinary.uploader()
                    .destroy(
                            publicId,
                            ObjectUtils.asMap(
                                    "resource_type", resourceType,
                                    "invalidate", true));

            String resultStatus = (String) destroyResult.get("result");
            boolean isDeleted = resultStatus.equals("ok");
            if (isDeleted) {
                log.info("File deleted successfully: {}", publicId);
            } else {
                log.warn("File deletion returned unexpected result: {}", resultStatus);
            }
            return isDeleted;

        } catch (IOException e) {
            log.error("Failed to delete file: {}", publicId, e);
            throw new RuntimeException("Delete failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error while deleting file: {}", publicId, e);
            throw new RuntimeException("Delete failed: " + e.getMessage());
        }
    }

    public boolean deleteImage(String publicId) {
        return deleteFile(publicId, "image");
    }

    public boolean deletePdf(String publicId) {
        return deleteFile(publicId, "raw");
    }

}
