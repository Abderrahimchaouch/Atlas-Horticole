package com.atlashorticole.product_service.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.atlashorticole.product_service.Mapper.FileMapper;
import com.atlashorticole.product_service.domain.File;
import com.atlashorticole.product_service.domain.FileType;
import com.atlashorticole.product_service.domain.Product;
import com.atlashorticole.product_service.dto.FileDTO;
import com.atlashorticole.product_service.repository.FileRepository;
import com.atlashorticole.product_service.repository.ProductRepository;
import com.atlashorticole.product_service.utils.CloudinaryUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;
    private final FileMapper fileMapper;

    @Override
    @Transactional
    public FileDTO uploadFile(MultipartFile file, FileType fileType, Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        CloudinaryUtils.validateFile(file, fileType == FileType.PDF ? "PDF" : "IMAGE");

        String fileUrl;
        String publicId;

        if (fileType == FileType.PDF) {
            fileUrl = cloudinaryService.uploadPdf(file);
        } else {
            fileUrl = cloudinaryService.uploadImage(file);
        }

        publicId = CloudinaryUtils.extractPublicId(fileUrl);

        File fileEntity = File.builder()
                .originalName(file.getOriginalFilename())
                .fileUrl(fileUrl)
                .publicId(publicId)
                .size(file.getSize())
                .fileType(fileType)
                .product(product)
                .build();

        File savedFile = fileRepository.save(fileEntity);

        return fileMapper.toDto(savedFile);
    }

    @Override
    @Transactional
    public void deleteFile(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("File not found with id: " + fileId));

        String resourceType = file.getFileType() == FileType.PDF ? "raw" : "image";
        cloudinaryService.deleteFile(file.getPublicId(), resourceType);

        fileRepository.delete(file);
    }

    @Override
    public List<FileDTO> getFilesByProductId(Long productId) {
        return fileRepository.findByProductId(productId).stream()
                .map(fileMapper::toDto)
                .toList();
    }

    @Override
    public List<FileDTO> getFilesByProductIdAndType(Long productId, FileType fileType) {
        return fileRepository.findByProductIdAndFileType(productId, fileType).stream()
                .map(fileMapper::toDto)
                .toList();
    }

    @Override
    public List<FileDTO> getAllFiles() {
        log.debug("Fetching all files from database");
        return fileRepository.findAll().stream()
                .map(fileMapper::toDto)
                .toList();
    }
}