package com.atlashorticole.product_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atlashorticole.product_service.domain.File;
import com.atlashorticole.product_service.domain.FileType;


@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    
    public List<File> findByProductId(Long productId);
    
    public List<File> findByProductIdAndFileType(Long productId, FileType fileType);
    
    public Optional<File> findByPublicId(String publicId);
    

    default List<File> findImagesByProductId(Long productId) {
        return findByProductIdAndFileType(productId, FileType.PDF);
    }
    

    default List<File> findPdfsByProductId(Long productId) {
        return findByProductIdAndFileType(productId, FileType.IMAGE);
    }
}