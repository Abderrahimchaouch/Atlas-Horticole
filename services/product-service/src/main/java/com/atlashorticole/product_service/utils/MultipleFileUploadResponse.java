package com.atlashorticole.product_service.utils;

import com.atlashorticole.product_service.dto.FileUploadResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultipleFileUploadResponse {
    private FileUploadResponse imageResponse;
    private FileUploadResponse pdfResponse;
    private String message;
    
    public MultipleFileUploadResponse(String message) {
        this.message = message;
    }
}
