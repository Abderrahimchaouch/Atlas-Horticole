package com.atlashorticole.product_service.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.atlashorticole.product_service.dto.FileUploadResponse;
import com.atlashorticole.product_service.services.CloudinaryService;
import com.atlashorticole.product_service.utils.CloudinaryUtils;
import com.atlashorticole.product_service.utils.HealthCheckResponse;
import com.atlashorticole.product_service.utils.MultipleFileUploadResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cloudinary")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Cloudinary File Upload", description = "API for uploading and managing files on Cloudinary")
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    @Operation(summary = "Upload an image", description = "Upload an image to Cloudinary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file or unsupported type"),
            @ApiResponse(responseCode = "413", description = "File too large"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponse> uploadImage(
            @Parameter(description = "Image file to upload (JPG, PNG, GIF, etc.)") @RequestParam("file") @NotNull MultipartFile file) {

        log.info("Image upload request received: {}", file.getOriginalFilename());

        // Validation
        CloudinaryUtils.validateFile(file, "image");

        try {
            // Upload to Cloudinary
            String fileUrl = cloudinaryService.uploadImage(file);

            // Extract publicId from URL
            String publicId = CloudinaryUtils.extractPublicId(fileUrl);

            // Create response
            FileUploadResponse response = FileUploadResponse.builder()
                    .fileName(file.getOriginalFilename())
                    .fileUrl(fileUrl)
                    .fileType("image")
                    .fileSize(file.getSize())
                    .publicId(publicId)
                    .message("Image uploaded successfully")
                    .build();

            log.info("Image uploaded successfully: {}", fileUrl);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Failed to upload image: {}", file.getOriginalFilename(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FileUploadResponse.builder()
                            .fileName(file.getOriginalFilename())
                            .message("Upload failed: " + e.getMessage())
                            .build());
        }
    }

    @Operation(summary = "Upload a PDF", description = "Upload a PDF document to Cloudinary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PDF uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file or unsupported type"),
            @ApiResponse(responseCode = "413", description = "File too large"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/upload/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponse> uploadPdf(
            @Parameter(description = "PDF file to upload") @RequestParam("file") @NotNull MultipartFile file) {

        log.info("PDF upload request received: {}", file.getOriginalFilename());

        // Validation
        CloudinaryUtils.validateFile(file, "PDF");

        try {
            // Upload to Cloudinary
            String fileUrl = cloudinaryService.uploadPdf(file);

            // Extract publicId from URL
            String publicId = CloudinaryUtils.extractPublicId(fileUrl);

            // Create response
            FileUploadResponse response = FileUploadResponse.builder()
                    .fileName(file.getOriginalFilename())
                    .fileUrl(fileUrl)
                    .fileType("pdf")
                    .fileSize(file.getSize())
                    .publicId(publicId)
                    .message("PDF uploaded successfully")
                    .build();

            log.info("PDF uploaded successfully: {}", fileUrl);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Failed to upload PDF: {}", file.getOriginalFilename(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(FileUploadResponse.builder()
                            .fileName(file.getOriginalFilename())
                            .message("Upload failed: " + e.getMessage())
                            .build());
        }
    }

    @Operation(summary = "Multiple upload", description = "Upload an image and a PDF in a single request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid files"),
            @ApiResponse(responseCode = "413", description = "Files too large"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(value = "/upload/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MultipleFileUploadResponse> uploadMultiple(
            @Parameter(description = "Product image") @RequestParam(value = "image", required = false) MultipartFile image,
            @Parameter(description = "Technical sheet PDF") @RequestParam(value = "technicalSheet", required = false) MultipartFile technicalSheet) {

        log.info("Multiple upload request received - image: {}, pdf: {}",
                image != null ? image.getOriginalFilename() : "null",
                technicalSheet != null ? technicalSheet.getOriginalFilename() : "null");

        MultipleFileUploadResponse response = new MultipleFileUploadResponse();

        try {
            if (image != null && !image.isEmpty()) {
                CloudinaryUtils.validateFile(image, "image");
                String imageUrl = cloudinaryService.uploadImage(image);
                response.setImageResponse(FileUploadResponse.builder()
                        .fileName(image.getOriginalFilename())
                        .fileUrl(imageUrl)
                        .fileType("image")
                        .fileSize(image.getSize())
                        .publicId(CloudinaryUtils.extractPublicId(imageUrl))
                        .message("Image uploaded successfully")
                        .build());
            }

            if (technicalSheet != null && !technicalSheet.isEmpty()) {
                CloudinaryUtils.validateFile(technicalSheet, "PDF");
                String pdfUrl = cloudinaryService.uploadPdf(technicalSheet);
                response.setPdfResponse(FileUploadResponse.builder()
                        .fileName(technicalSheet.getOriginalFilename())
                        .fileUrl(pdfUrl)
                        .fileType("pdf")
                        .fileSize(technicalSheet.getSize())
                        .publicId(CloudinaryUtils.extractPublicId(pdfUrl))
                        .message("PDF uploaded successfully")
                        .build());
            }

            if (response.getImageResponse() == null && response.getPdfResponse() == null) {
                return ResponseEntity.badRequest()
                        .body(new MultipleFileUploadResponse("No file provided"));
            }

            response.setMessage("Multiple upload successful");
            log.info("Multiple upload successful");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Failed to upload multiple files", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MultipleFileUploadResponse("Upload failed: " + e.getMessage()));
        }
    }

    @Operation(summary = "Test Cloudinary connection", description = "Check if Cloudinary is configured correctly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cloudinary is working"),
            @ApiResponse(responseCode = "500", description = "Cloudinary configuration problem")
    })
    @GetMapping("/health")
    public ResponseEntity<HealthCheckResponse> healthCheck() {
        log.info("Cloudinary health check requested");

        try {
            // Try minimal upload (empty file) to test
            // Or simply check if the service is injected
            if (cloudinaryService != null) {
                return ResponseEntity.ok(new HealthCheckResponse(
                        "Cloudinary service is operational",
                        "OK",
                        System.currentTimeMillis()));
            } else {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(new HealthCheckResponse(
                                "Cloudinary service not available",
                                "ERROR",
                                System.currentTimeMillis()));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new HealthCheckResponse(
                            "Cloudinary error: " + e.getMessage(),
                            "ERROR",
                            System.currentTimeMillis()));
        }
    }

    @Operation(summary = "Delete a file", description = "Delete a file from Cloudinary using its publicId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "file deleted successfully"),
            @ApiResponse(responseCode = "404", description = "File not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteFile(
            @Parameter(description = "Public ID of the file to delete") @RequestParam("publicId") @NotNull String publicId,
            @Parameter(description = "Resource type (image , raw for  documents)") @RequestParam(value = "resourceType", defaultValue = "image") String resourceType

    ) {

        log.info("Delete file request publicId {}, resourceType : {}", publicId, resourceType);
        Map<String, Object> response = new HashMap<>();
        try {

            boolean isDelted = cloudinaryService.deleteFile(publicId, resourceType);

            if (isDelted) {
                response.put("message", "File deleted successfully");
                response.put("publicId", publicId);
                response.put("status", "DELETED");
                response.put("timestamp", System.currentTimeMillis());
                log.info("File deleted successfully: {}", publicId);
                return ResponseEntity.ok(response);
            } else {

                response.put("message", "File not found or could not be deleted");
                response.put("publicId", publicId);
                response.put("status", "NOT_FOUND");
                log.warn("File not found: {}", publicId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

        } catch (Exception e) {

            log.error("Failed to delete file: {}", publicId, e);

            response.put("message", "Delete failed: " + e.getMessage());
            response.put("publicId", publicId);
            response.put("status", "ERROR");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Operation(summary = "Delete an image", description = "Delete an image from cloudinary using its public ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Image not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/delete/image")
    public ResponseEntity<Map<String, Object>> delteImage(
            @Parameter(description = "public ID of the image to delete") @RequestParam("publicId") String publicId) {
        return deleteFile(publicId, "image");
    }

    @Operation(summary = "Delete a PDF", description = "Delete a PDF from Cloudinary using its public ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PDF deleted successfully"),
            @ApiResponse(responseCode = "404", description = "PDF not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/delete/pdf")
    public ResponseEntity<Map<String, Object>> deletePdf(
            @Parameter(description = "Public ID of the PDF to delete") @RequestParam("publicId") @NotNull String publicId) {

        return deleteFile(publicId, "raw");
    }

}