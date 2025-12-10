package com.atlashorticole.product_service.services;

import com.atlashorticole.product_service.domain.FileType;
import com.atlashorticole.product_service.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface FileService {
    FileDTO uploadFile(MultipartFile file, FileType fileType, Long productId);
    void deleteFile(Long fileId);
    List<FileDTO> getFilesByProductId(Long productId);
    List<FileDTO> getFilesByProductIdAndType(Long productId, FileType fileType);
    List<FileDTO> getAllFiles();
}