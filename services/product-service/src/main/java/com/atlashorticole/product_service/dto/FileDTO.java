package com.atlashorticole.product_service.dto;

import com.atlashorticole.product_service.domain.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDTO {
    private Long id;
    private String originalName;
    private String fileUrl;
    private String publicId;
    private Long size;
    private FileType fileType;
    private Long productId;
}