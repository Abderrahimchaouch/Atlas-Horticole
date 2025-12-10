package com.atlashorticole.product_service.Mapper;

import org.springframework.stereotype.Component;
import com.atlashorticole.product_service.domain.File;
import com.atlashorticole.product_service.dto.FileDTO;

@Component
public class FileMapper {
    
    public FileDTO toDto(File file) {
        if (file == null) return null;
        return FileDTO.builder()
                .id(file.getId())
                .originalName(file.getOriginalName())
                .fileUrl(file.getFileUrl())
                .publicId(file.getPublicId())
                .size(file.getSize())
                .fileType(file.getFileType())
                .productId(file.getProduct() != null ? file.getProduct().getId() : null)
                .build();
    }
    
    public File toEntity(FileDTO dto) {
        if (dto == null) return null;
        return File.builder()
                .id(dto.getId())
                .originalName(dto.getOriginalName())
                .fileUrl(dto.getFileUrl())
                .publicId(dto.getPublicId())
                .size(dto.getSize())
                .fileType(dto.getFileType())
                .build();
    }
}