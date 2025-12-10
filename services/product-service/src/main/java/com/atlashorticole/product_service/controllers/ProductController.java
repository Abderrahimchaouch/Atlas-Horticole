package com.atlashorticole.product_service.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.domain.FileType;
import com.atlashorticole.product_service.dto.FileDTO;
import com.atlashorticole.product_service.dto.ProductDTO;
import com.atlashorticole.product_service.services.FileService;
import com.atlashorticole.product_service.services.ProductService;
import com.atlashorticole.product_service.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST Controller for managing agricultural products.
 * Provides CRUD operations, file management, and product categorization.
 */
@Slf4j
@RestController
@RequestMapping("/api/products")
@Validated
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "APIs for managing agricultural products and their associated files")
public class ProductController {

    private final ProductService productService;
    private final FileService fileService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // ========== CREATE OPERATIONS (FIRST - SPECIFIC PATHS) ==========

    @Operation(summary = "Create product with files (JSON)", description = "Create a new product with optional image and PDF files using JSON")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created with files successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or file format")
    })
    @PostMapping(value = "/create-with-files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> createProductWithFiles(
            @Parameter(description = "Product data in JSON format") @RequestParam("product") String productJson,
            @Parameter(description = "Product image file") @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @Parameter(description = "Technical sheet PDF file") @RequestParam(value = "pdf", required = false) MultipartFile pdfFile) {

        String image = imageFile != null && !imageFile.isEmpty() ? imageFile.getOriginalFilename() : "No image";
        String pdf = pdfFile != null && !pdfFile.isEmpty() ? pdfFile.getOriginalFilename() : "No PDF";

        log.info("Request received - JSON length: {}, image: {}, pdf: {}",
                productJson.length(), image, pdf);

        List<MultipartFile> files = new ArrayList<>();
        List<FileType> fileTypes = new ArrayList<>();

        if (pdfFile != null && !pdfFile.isEmpty()) {
            files.add(pdfFile);
            fileTypes.add(FileType.PDF);
            log.debug("Added PDF: {}", pdfFile.getOriginalFilename());
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            files.add(imageFile);
            fileTypes.add(FileType.IMAGE);
            log.debug("Added image: {}", imageFile.getOriginalFilename());
        }

        try {
            ProductDTO dto = objectMapper.readValue(productJson, ProductDTO.class);
            log.debug("Parsed product: {}", dto.getName());

            ProductDTO createdProduct;
            if (!files.isEmpty()) {
                createdProduct = productService.createProductWithFiles(dto, files, fileTypes);
                log.info("Product created with {} file(s), ID: {}", files.size(), createdProduct.getId());
            } else {
                createdProduct = productService.createNewProduct(dto);
                log.info("Product created without files, ID: {}", createdProduct.getId());
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            log.error("Failed to create product: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product JSON format");
        }
    }

    @Operation(summary = "Create product with files (simple)", description = "Create product with files using individual form parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping(value = "/create-simple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> createProductSimple(
            @Parameter(description = "Product name") @RequestParam("name") String name,
            @Parameter(description = "Product category") @RequestParam("category") Category category,
            @Parameter(description = "Product description") @RequestParam(value = "description", required = false) String description,
            @Parameter(description = "Product dosage instructions") @RequestParam(value = "dosage", required = false) String dosage,
            @Parameter(description = "Product summary") @RequestParam(value = "resume", required = false) String resume,
            @Parameter(description = "Product image file") @RequestParam(value = "image", required = false) MultipartFile imageFile,
            @Parameter(description = "Product PDF file") @RequestParam(value = "pdf", required = false) MultipartFile pdfFile,
            @Parameter(description = "Display order priority") @RequestParam(value = "displayOrder", required = false) Integer displayOrder,
            @Parameter(description = "Product active status") @RequestParam(value = "active", required = false, defaultValue = "true") Boolean active) {

        log.info("Creating product - Name: {}, Category: {}", name, category);

        ProductDTO dto = ProductDTO.builder()
                .name(name)
                .resume(resume)
                .dosage(dosage)
                .category(category)
                .description(description)
                .displayOrder(displayOrder)
                .active(active)
                .build();

        List<MultipartFile> files = new ArrayList<>();
        List<FileType> fileTypes = new ArrayList<>();

        if (pdfFile != null && !pdfFile.isEmpty()) {
            files.add(pdfFile);
            fileTypes.add(FileType.PDF);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            files.add(imageFile);
            fileTypes.add(FileType.IMAGE);
        }

        ProductDTO createdProduct;
        if (!files.isEmpty()) {
            createdProduct = productService.createProductWithFiles(dto, files, fileTypes);
            log.info("Product created with {} file(s)", files.size());
        } else {
            createdProduct = productService.createNewProduct(dto);
            log.info("Product created without files");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @Operation(summary = "Create product", description = "Create a new product without files")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @Parameter(description = "Product data") @Valid @RequestBody ProductDTO dto) {
        log.info("Creating product: {}", dto.getName());
        ProductDTO created = productService.createNewProduct(dto);
        log.info("Product created with ID: {}", created.getId());
        return ResponseEntity.created(URI.create("/api/products/" + created.getId())).body(created);
    }

    // ========== READ OPERATIONS (SECOND - COLLECTIONS) ==========

    @Operation(summary = "Get all products", description = "Retrieve all products without pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All products retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.debug("Fetching all products");
        List<ProductDTO> result = productService.findALL();
        log.debug("Found {} products", result.size());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get paginated products", description = "Retrieve products with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping("/page")
    public ResponseEntity<Page<ProductDTO>> getProductsByPage(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field and direction (field,direction)") @RequestParam(defaultValue = "id,asc") String sort) {

        log.debug("Fetching products page: {}, size: {}, sort: {}", page, size, sort);
        Sort sortOrder = Utils.checkParamSort(sort);
        Page<ProductDTO> result = productService.findByPage(PageRequest.of(page, size, sortOrder));
        log.debug("Found {} products on page {}", result.getContent().size(), page);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get all files", description = "Retrieve all files from database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files retrieved successfully")
    })
    @GetMapping("/files")
    public ResponseEntity<List<FileDTO>> getAllFiles() {
        log.info("Fetching all files");
        List<FileDTO> files = fileService.getAllFiles();
        return ResponseEntity.ok(files);
    }

    @Operation(summary = "Get products by category", description = "Retrieve products filtered by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<ProductDTO>> getProductsByCategory(
            @Parameter(description = "Product category") @PathVariable Category category,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {

        log.debug("Fetching products by category: {}, page: {}", category, page);
        Page<ProductDTO> result = productService.findByCategory(category, PageRequest.of(page, size));
        log.debug("Found {} products in category {}", result.getContent().size(), category);
        return ResponseEntity.ok(result);
    }

    // ========== INDIVIDUAL PRODUCT OPERATIONS (THIRD - WITH ID) ==========

    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        log.debug("Fetching product with ID: {}", id);
        return productService.findById(id)
                .map(product -> {
                    log.debug("Product found: {}", product.getName());
                    return ResponseEntity.ok(product);
                })
                .orElseGet(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Get product with files", description = "Retrieve a product with all associated files loaded")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product with files retrieved"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}/with-files")
    public ResponseEntity<ProductDTO> getProductWithFiles(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        log.debug("Fetching product with files, ID: {}", id);
        return productService.findByIdWithFiles(id)
                .map(product -> {
                    log.debug("Product found with {} files",
                            product.getFiles() != null ? product.getFiles().size() : 0);
                    return ResponseEntity.ok(product);
                })
                .orElseGet(() -> {
                    log.warn("Product not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Update product", description = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Parameter(description = "Updated product data") @Valid @RequestBody ProductDTO dto) {
        log.info("Updating product ID: {}, new name: {}", id, dto.getName());
        ProductDTO updatedDto = productService.updateProduct(id, dto);
        log.info("Product updated successfully, ID: {}", id);
        return ResponseEntity.ok(updatedDto);
    }

    @Operation(summary = "Delete product", description = "Delete a product and all associated files")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        log.info("Deleting product with ID: {}", id);
        productService.delete(id);
        log.info("Product deleted successfully, ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    // ========== FILE OPERATIONS ON PRODUCT (FOURTH - SUB-RESOURCES) ==========

    @Operation(summary = "Upload file to product", description = "Upload an image or PDF file to an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid file type or size")
    })
    @PostMapping(value = "/{id}/upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileDTO> uploadFileToProduct(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Parameter(description = "File to upload") @RequestParam("file") MultipartFile file,
            @Parameter(description = "File type (IMAGE or PDF)") @RequestParam("fileType") FileType fileType) {

        log.info("Uploading file to product ID: {}, type: {}, file: {}",
                id, fileType, file.getOriginalFilename());

        FileDTO uploadedFile = fileService.uploadFile(file, fileType, id);

        log.info("File uploaded successfully, ID: {}, URL: {}",
                uploadedFile.getId(), uploadedFile.getFileUrl());

        return ResponseEntity.ok(uploadedFile);
    }

    @Operation(summary = "Get product files", description = "Retrieve all files associated with a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}/files")
    public ResponseEntity<List<FileDTO>> getProductFiles(
            @Parameter(description = "Product ID") @PathVariable Long id) {
        log.debug("Fetching files for product ID: {}", id);
        List<FileDTO> files = fileService.getFilesByProductId(id);
        log.debug("Found {} files for product ID: {}", files.size(), id);
        return ResponseEntity.ok(files);
    }

    @Operation(summary = "Get product files by type", description = "Retrieve files of a specific type associated with a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Files retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}/files/{fileType}")
    public ResponseEntity<List<FileDTO>> getProductFilesByType(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Parameter(description = "File type (IMAGE or PDF)") @PathVariable FileType fileType) {
        log.debug("Fetching {} files for product ID: {}", fileType, id);
        List<FileDTO> files = fileService.getFilesByProductIdAndType(id, fileType);
        log.debug("Found {} {} files for product ID: {}", files.size(), fileType, id);
        return ResponseEntity.ok(files);
    }

    @Operation(summary = "Delete product file", description = "Delete a specific file from a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "File deleted successfully"),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    @DeleteMapping("/{id}/files/{fileId}")
    public ResponseEntity<Void> deleteProductFile(
            @Parameter(description = "Product ID") @PathVariable Long id,
            @Parameter(description = "File ID") @PathVariable Long fileId) {
        log.info("Deleting file ID: {} from product ID: {}", fileId, id);
        fileService.deleteFile(fileId);
        log.info("File deleted successfully, ID: {}", fileId);
        return ResponseEntity.noContent().build();
    }
}