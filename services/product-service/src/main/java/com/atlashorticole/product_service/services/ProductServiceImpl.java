package com.atlashorticole.product_service.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.atlashorticole.product_service.Mapper.ProductMapper;
import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.domain.File;
import com.atlashorticole.product_service.domain.FileType;
import com.atlashorticole.product_service.domain.Product;
import com.atlashorticole.product_service.dto.FileDTO;
import com.atlashorticole.product_service.dto.ProductDTO;
import com.atlashorticole.product_service.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for Product management operations.
 * Handles CRUD operations, pagination, filtering by category, 
 * and integration with Cloudinary for file management.
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    private final CloudinaryService cloudinaryService;
    private final FileService fileService;
    /**
     * Constructor for dependency injection.
     * 
     * @param productRepository Repository for product data access
     * @param mapper Mapper for converting between Product and ProductDTO
     * @param cloudinaryService Service for Cloudinary file operations
     * @param fileService
     */
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, 
                              ProductMapper mapper,
                              CloudinaryService cloudinaryService, FileService fileService) {
        this.productRepository = productRepository;
        this.mapper = mapper;
        this.cloudinaryService = cloudinaryService;
        this.fileService = fileService;
    }

    /**
     * Creates a new product from the provided DTO.
     * 
     * @param dto Product data transfer object
     * @return Created product as DTO
     */
    @Override
    public ProductDTO createNewProduct(ProductDTO dto) {
        Product product = mapper.toEntity(dto);
        if (product != null) {
            Product savedProduct = this.productRepository.save(product);
            return mapper.toDto(savedProduct);
        }
        return null;
    }

    /**
     * Retrieves a product by its ID.
     * 
     * @param id Product identifier
     * @return Optional containing ProductDTO if found, empty otherwise
     */
    @Override
    public Optional<ProductDTO> findById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductDTO dto = mapper.toDto(product.get());
            return Optional.of(dto);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Retrieves products with pagination support.
     * 
     * @param page Pagination information (page number, size, sorting)
     * @return Page of ProductDTOs
     */
    @Override
    public Page<ProductDTO> findByPage(Pageable page) {
        return productRepository.findAll(page).map(mapper::toDto);
    }

    /**
     * Retrieves all products without pagination.
     * 
     * @return List of all ProductDTOs
     */
    @Override
    public List<ProductDTO> findALL() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Retrieves products filtered by category with pagination.
     * 
     * @param category Product category filter
     * @param page Pagination information
     * @return Page of ProductDTOs in the specified category
     */
    @Override
    public Page<ProductDTO> findByCategory(Category category, Pageable page) {
        return productRepository.findByCategory(category, page).map(mapper::toDto);
    }

    /**
     * Updates an existing product.
     * 
     * @param id Product identifier to update
     * @param dto Updated product data
     * @return Updated ProductDTO
     * @throws IllegalArgumentException if product not found
     */
    @Override
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
            String file = dto.getFiles()!=null?" non":"oui";
        mapper.updateEntityFromDto(dto, existingProduct);
        Product savedProduct = productRepository.save(existingProduct);
        return mapper.toDto(savedProduct);
    }

    /**
     * Deletes a product and its associated files from both database and Cloudinary.
     * 
     * @param id Product identifier to delete
     * @throws ResponseStatusException if product not found
     */
    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, 
                    "Product not found with ID: " + id
                ));
        
        // Delete associated files from Cloudinary
        for (File file : product.getFiles()) {
            String resourceType = file.getFileType() == FileType.PDF ? "raw" : "image";
            cloudinaryService.deleteFile(file.getPublicId(), resourceType);
        }
        
        // Delete product from database (files will be deleted by cascade)
        productRepository.delete(product);
    }

    /**
     * Retrieves a product with its associated files by ID.
     * 
     * @param product_id Product identifier
     * @return Optional containing ProductDTO with files if found
     */
    @Override
    public Optional<ProductDTO> findByIdWithFiles(Long product_id) {
        return productRepository.findByIdWithFiles(product_id)
                .map(mapper::toDto);
    }

/**
 * Creates a new product with a single attached file.
 * 
 * @param dto Product data transfer object containing product information
 * @param file Multipart file to be uploaded and associated with the product
 * @param fileType Type of the file (IMAGE or PDF) as defined in FileType enum
 * @return Complete ProductDTO with uploaded file information
 */
@Override
@Transactional
public ProductDTO createProductWithFile(ProductDTO dto, MultipartFile file, FileType fileType) {
    if (dto == null) {
        throw new IllegalArgumentException("ProductDTO cannot be null");
    }
    if (file == null || file.isEmpty()) {
        throw new IllegalArgumentException("File cannot be null or empty");
    }
    if (fileType == null) {
        throw new IllegalArgumentException("FileType cannot be null");
    }
    
    // 1. Create product
    ProductDTO product = createNewProduct(dto);
    
    // 2. Upload file
    FileDTO fileDTO = fileService.uploadFile(file, fileType, product.getId());
    
    // 3. Return product with files
    return findById(product.getId()).orElseThrow(
        () -> new RuntimeException("Failed to retrieve created product")
    );
}
/**
 * Creates a new product with multiple attached files.
 * This method handles batch file uploads and ensures transactional consistency -
 * if any file upload fails, the entire operation is rolled back.
 * 
 * @param dto Product data transfer object containing product information
 * @param files List of Multipart files to be uploaded
 * @param fileTypes List of file types corresponding to each file
 */
@Override
@Transactional
public ProductDTO createProductWithFiles(ProductDTO dto, List<MultipartFile> files, List<FileType> fileTypes) {
    if (dto == null) {
        throw new IllegalArgumentException("ProductDTO cannot be null");
    }
    if (files == null || fileTypes == null) {
        throw new IllegalArgumentException("Files and fileTypes cannot be null");
    }
    if (files.size() != fileTypes.size()) {
        throw new IllegalArgumentException("Files and fileTypes must have the same size");
    }
    
       ProductDTO createdProduct = createNewProduct(dto);
    
    if (createdProduct == null) {
        throw new RuntimeException("Failed to create product");
    }
    
    Long productId = createdProduct.getId();
    
    // 2. Upload all files...
    for (int i = 0; i < files.size(); i++) {
        if (files.get(i) != null && !files.get(i).isEmpty()) {
            try {
                fileService.uploadFile(files.get(i), fileTypes.get(i), productId);
            } catch (Exception e) {
                // Delete product if file upload fails
                productRepository.deleteById(productId);
                throw new RuntimeException("Failed to upload file: " + files.get(i).getOriginalFilename(), e);
            }
        }
    }
    
    // 3. Return complete product
    return findById(productId).orElseThrow(
        () -> new RuntimeException("Failed to retrieve created product")
    );
}

}