package com.atlashorticole.product_service.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.atlashorticole.product_service.domain.FileType;
import com.atlashorticole.product_service.dto.FileDTO;

public interface FileService {
    FileDTO uploadFile(MultipartFile file, FileType fileType, Long productId);

    void deleteFile(Long fileId);

    List<FileDTO> getFilesByProductId(Long productId);

    List<FileDTO> getFilesByProductIdAndType(Long productId, FileType fileType);

    List<FileDTO> getAllFiles();
}