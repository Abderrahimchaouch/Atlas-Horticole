package com.atlashorticole.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileUploadResponse {
    private String fileName;
    private String fileUrl;
    private String fileType;  // "image" or "pdf"
    private Long fileSize;    //  bytes
    private String message;
    private String publicId;  // ID Cloudinary for suppression
}
