package com.atlashorticole.product_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.domain.FileType;
import com.atlashorticole.product_service.dto.ProductDTO;

public interface ProductService {

    ProductDTO createNewProduct(ProductDTO dto);

    Optional<ProductDTO> findById(Long id);
    Page<ProductDTO> findByPage(Pageable page);
    List<ProductDTO>  findALL();
    Page<ProductDTO> findByCategory(Category category,Pageable page);
    ProductDTO updateProduct(Long id , ProductDTO dto);
    void delete(Long id);
    Optional<ProductDTO> findByIdWithFiles(Long id);
    /**
     * Creates a product with a single file.
     */
    ProductDTO createProductWithFile(ProductDTO dto, MultipartFile file, FileType fileType);
    
    /**
     * Creates a product with multiple files.
     */
    ProductDTO createProductWithFiles(ProductDTO dto, List<MultipartFile> files, List<FileType> fileTypes);
    

    
}
