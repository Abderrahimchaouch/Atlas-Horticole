package com.atlashorticole.product_service.services;

import com.atlashorticole.product_service.Mapper.ProductMapper;
import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.domain.File;
import com.atlashorticole.product_service.domain.FileType;
import com.atlashorticole.product_service.domain.Product;
import com.atlashorticole.product_service.dto.FileDTO;
import com.atlashorticole.product_service.dto.ProductDTO;
import com.atlashorticole.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    
    @Mock
    private ProductMapper mapper;
    
    @Mock
    private CloudinaryService cloudinaryService;
    
    @Mock
    private FileService fileService;
    
    @InjectMocks
    private ProductServiceImpl productService;
    
    @Captor
    private ArgumentCaptor<Product> productCaptor;
    
    private Product testProduct;
    private ProductDTO testProductDTO;
    private File testFile;
    private MultipartFile testMultipartFile;
    
    @BeforeEach
    public void setUp() {
        // Setup test data
        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .category(Category.BIOSTIMULANT)
                .build();
        
        testProductDTO = ProductDTO.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .category(Category.BIOSTIMULANT)
                .build();
        
        testFile = File.builder()
                .id(1L)
                .publicId("file_public_id")
                .fileType(FileType.IMAGE)
                .build();
        
        testProduct.getFiles().add(testFile);
        
        testMultipartFile = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "Test Image Content".getBytes()
        );
    }
    
    // Test createNewProduct
    @Test
    public void createNewProduct_WithValidDTO_ShouldReturnSavedProductDTO() {
        // Arrange
        when(mapper.toEntity(testProductDTO)).thenReturn(testProduct);
        when(productRepository.save(testProduct)).thenReturn(testProduct);
        when(mapper.toDto(testProduct)).thenReturn(testProductDTO);
        
        // Act
        ProductDTO result = productService.createNewProduct(testProductDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals(testProductDTO, result);
        
        verify(mapper).toEntity(testProductDTO);
        verify(productRepository).save(testProduct);
        verify(mapper).toDto(testProduct);
    }
    
    @Test
    public void createNewProduct_WhenMapperReturnsNull_ShouldReturnNull() {
        // Arrange
        when(mapper.toEntity(testProductDTO)).thenReturn(null);
        
        // Act
        ProductDTO result = productService.createNewProduct(testProductDTO);
        
        // Assert
        assertNull(result);
        verify(productRepository, never()).save(any());
        verify(mapper, never()).toDto(any());
    }
    
    // Test findById
    @Test
    public void findById_WithExistingId_ShouldReturnProductDTO() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(mapper.toDto(testProduct)).thenReturn(testProductDTO);
        
        // Act
        Optional<ProductDTO> result = productService.findById(1L);
        
        // Assert
        assertTrue(result.isPresent());
        assertEquals(testProductDTO, result.get());
        verify(productRepository).findById(1L);
        verify(mapper).toDto(testProduct);
    }
    
    @Test
    public void findById_WithNonExistingId_ShouldReturnEmptyOptional() {
        // Arrange
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act
        Optional<ProductDTO> result = productService.findById(999L);
        
        // Assert
        assertFalse(result.isPresent());
        verify(productRepository).findById(999L);
        verify(mapper, never()).toDto(any());
    }
    
    // Test findByPage
    @Test
    public void findByPage_ShouldReturnPagedProductDTOs() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(List.of(testProduct), pageable, 1);
        Page<ProductDTO> expectedPage = new PageImpl<>(List.of(testProductDTO), pageable, 1);
        
        when(productRepository.findAll(pageable)).thenReturn(productPage);
        when(mapper.toDto(testProduct)).thenReturn(testProductDTO);
        
        // Act
        Page<ProductDTO> result = productService.findByPage(pageable);
        
        // Assert
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(testProductDTO);
        verify(productRepository).findAll(pageable);
        verify(mapper).toDto(testProduct);
    }
    
    // Test findAll
    @Test
    public void findAll_ShouldReturnListOfProductDTOs() {
        // Arrange
        List<Product> products = List.of(testProduct);
        when(productRepository.findAll()).thenReturn(products);
        when(mapper.toDto(testProduct)).thenReturn(testProductDTO);
        
        // Act
        List<ProductDTO> result = productService.findALL();
        
        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(testProductDTO);
        verify(productRepository).findAll();
        verify(mapper).toDto(testProduct);
    }
    
    // Test findByCategory
    @Test
    public void findByCategory_ShouldReturnFilteredProductDTOs() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> productPage = new PageImpl<>(List.of(testProduct), pageable, 1);
        Page<ProductDTO> expectedPage = new PageImpl<>(List.of(testProductDTO), pageable, 1);
        
        when(productRepository.findByCategory(Category.AMENDMENT, pageable)).thenReturn(productPage);
        when(mapper.toDto(testProduct)).thenReturn(testProductDTO);
        
        // Act
        Page<ProductDTO> result = productService.findByCategory(Category.AMENDMENT, pageable);
        
        // Assert
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getCategory()).isEqualTo(Category.BIOSTIMULANT);
        verify(productRepository).findByCategory(Category.AMENDMENT, pageable);
        verify(mapper).toDto(testProduct);
    }
    
    // Test updateProduct
    @Test
    public void updateProduct_WithExistingId_ShouldReturnUpdatedProductDTO() {
        // Arrange
        ProductDTO updateDTO = ProductDTO.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated Description")
                .category(Category.COMPOST)
                .build();
        
        Product updatedProduct = Product.builder()
                .id(1L)
                .name("Updated Product")
                .description("Updated Description")
                .category(Category.COMPOST)
                .build();
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        doNothing().when(mapper).updateEntityFromDto(updateDTO, testProduct);
        when(productRepository.save(testProduct)).thenReturn(updatedProduct);
        when(mapper.toDto(updatedProduct)).thenReturn(updateDTO);
        
        // Act
        ProductDTO result = productService.updateProduct(1L, updateDTO);
        
        // Assert
        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        assertEquals("Updated Description", result.getDescription());

        assertEquals(Category.COMPOST, result.getCategory());
        
        verify(productRepository).findById(1L);
        verify(mapper).updateEntityFromDto(updateDTO, testProduct);
        verify(productRepository).save(testProduct);
        verify(mapper).toDto(updatedProduct);
    }
    
    @Test
    public void updateProduct_WithNonExistingId_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> productService.updateProduct(999L, testProductDTO));
        
        assertEquals("Product not found with id: 999", exception.getMessage());
        verify(productRepository).findById(999L);
        verify(mapper, never()).updateEntityFromDto(any(), any());
        verify(productRepository, never()).save(any());
    }
    
    // Test delete
    @Test
    public void delete_WithExistingId_ShouldDeleteProductAndFiles() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
    
        
        // Act
        productService.delete(1L);
        
        // Assert
        verify(productRepository).findById(1L);
        verify(cloudinaryService).deleteFile("file_public_id", "image");
        verify(productRepository).delete(testProduct);
    }
    
    @Test
    public void delete_WithPdfFile_ShouldUseRawResourceType() {
        // Arrange
        File pdfFile = File.builder()
                .publicId("pdf_public_id")
                .fileType(FileType.PDF)
                .build();
        
        Product productWithPdf = Product.builder()
                .id(2L)
                .name("Product with PDF")
                .build();
        
        productWithPdf.getFiles().add(pdfFile);
        
        when(productRepository.findById(2L)).thenReturn(Optional.of(productWithPdf));

        
        // Act
        productService.delete(2L);
        
        // Assert
        verify(cloudinaryService).deleteFile("pdf_public_id", "raw");
    }
    
    @Test
    public void delete_WithNonExistingId_ShouldThrowResponseStatusException() {
        // Arrange
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
            () -> productService.delete(999L));
        
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Product not found with ID: 999", exception.getReason());
        verify(cloudinaryService, never()).deleteFile(anyString(), anyString());
        verify(productRepository, never()).delete(any());
    }
    
    // Test findByIdWithFiles
    @Test
    public void findByIdWithFiles_ShouldReturnProductWithFiles() {
        // Arrange
        when(productRepository.findByIdWithFiles(1L)).thenReturn(Optional.of(testProduct));
        when(mapper.toDto(testProduct)).thenReturn(testProductDTO);
        
        // Act
        Optional<ProductDTO> result = productService.findByIdWithFiles(1L);
        
        // Assert
        assertTrue(result.isPresent());
        verify(productRepository).findByIdWithFiles(1L);
        verify(mapper).toDto(testProduct);
    }
    
    // Test createProductWithFile
    @Test
    public void createProductWithFile_WithValidInputs_ShouldCreateProductAndUploadFile() {
        // Arrange
        ProductDTO createdProductDTO = ProductDTO.builder().id(1L).build();
        FileDTO uploadedFileDTO = FileDTO.builder().id(1L).build();
        
        when(mapper.toEntity(testProductDTO)).thenReturn(testProduct);
        when(productRepository.save(testProduct)).thenReturn(testProduct);
        when(mapper.toDto(testProduct)).thenReturn(createdProductDTO, testProductDTO);
        
        when(fileService.uploadFile(testMultipartFile, FileType.IMAGE, 1L)).thenReturn(uploadedFileDTO);
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        
        // Act
        ProductDTO result = productService.createProductWithFile(testProductDTO, testMultipartFile, FileType.IMAGE);
        
        // Assert
        assertNotNull(result);
        assertEquals(testProductDTO, result);
        
        verify(mapper).toEntity(testProductDTO);
        verify(productRepository).save(testProduct);
        verify(fileService).uploadFile(testMultipartFile, FileType.IMAGE, 1L);
        verify(productRepository).findById(1L);
    }
    
    @Test
    public void createProductWithFile_WithNullProductDTO_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> productService.createProductWithFile(null, testMultipartFile, FileType.IMAGE));
        
        assertEquals("ProductDTO cannot be null", exception.getMessage());
        verify(fileService, never()).uploadFile(any(), any(), any());
    }
    
    @Test
    public void createProductWithFile_WithNullFile_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> productService.createProductWithFile(testProductDTO, null, FileType.IMAGE));
        
        assertEquals("File cannot be null or empty", exception.getMessage());
        verify(fileService, never()).uploadFile(any(), any(), any());
    }
    
    @Test
    public void createProductWithFile_WithNullFileType_ShouldThrowException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> productService.createProductWithFile(testProductDTO, testMultipartFile, null));
        
        assertEquals("FileType cannot be null", exception.getMessage());
        verify(fileService, never()).uploadFile(any(), any(), any());
    }
    
    @Test
    public void createProductWithFile_WhenFileUploadFails_ShouldPropagateException() {
        // Arrange
        when(mapper.toEntity(testProductDTO)).thenReturn(testProduct);
        when(productRepository.save(testProduct)).thenReturn(testProduct);
        when(mapper.toDto(testProduct)).thenReturn(ProductDTO.builder().id(1L).build());
        
        RuntimeException uploadException = new RuntimeException("Cloudinary error");
        when(fileService.uploadFile(testMultipartFile, FileType.IMAGE, 1L)).thenThrow(uploadException);
        
        // Act & Assert
        assertThrows(RuntimeException.class,
            () -> productService.createProductWithFile(testProductDTO, testMultipartFile, FileType.IMAGE));
        
        // Verify product was created before exception
        verify(productRepository).save(testProduct);
    }
    
    // Test createProductWithFiles
    @Test
    public void createProductWithFiles_WithValidInputs_ShouldCreateProductAndUploadMultipleFiles() {
        // Arrange
        List<MultipartFile> files = Arrays.asList(
            testMultipartFile,
            new MockMultipartFile("file2", "test2.jpg", "image/jpeg", "content2".getBytes())
        );
        List<FileType> fileTypes = Arrays.asList(FileType.IMAGE, FileType.IMAGE);
        
        ProductDTO createdProductDTO = ProductDTO.builder().id(1L).build();
        
        when(mapper.toEntity(testProductDTO)).thenReturn(testProduct);
        when(productRepository.save(testProduct)).thenReturn(testProduct);
        when(mapper.toDto(testProduct)).thenReturn(createdProductDTO, testProductDTO);
        
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        
        // Act
        ProductDTO result = productService.createProductWithFiles(testProductDTO, files, fileTypes);
        
        // Assert
        assertNotNull(result);
        verify(fileService, times(2)).uploadFile(any(MultipartFile.class), any(FileType.class), eq(1L));
        verify(productRepository, never()).deleteById(any());
    }
    
    @Test
    public void createProductWithFiles_WithMismatchedFileAndTypeCount_ShouldThrowException() {
        // Arrange
        List<MultipartFile> files = List.of(testMultipartFile);
        List<FileType> fileTypes = Arrays.asList(FileType.IMAGE, FileType.PDF); // Mismatch
        
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> productService.createProductWithFiles(testProductDTO, files, fileTypes));
        
        assertEquals("Files and fileTypes must have the same size", exception.getMessage());
        verify(fileService, never()).uploadFile(any(), any(), any());
    }
    
    @Test
    public void createProductWithFiles_WhenFileUploadFails_ShouldDeleteProductAndThrowException() {
        // Arrange
        List<MultipartFile> files = Arrays.asList(testMultipartFile, testMultipartFile);
        List<FileType> fileTypes = Arrays.asList(FileType.IMAGE, FileType.IMAGE);
        
        ProductDTO createdProductDTO = ProductDTO.builder().id(1L).build();
        
        when(mapper.toEntity(testProductDTO)).thenReturn(testProduct);
        when(productRepository.save(testProduct)).thenReturn(testProduct);
        when(mapper.toDto(testProduct)).thenReturn(createdProductDTO);
        
        // First upload succeeds, second fails
        RuntimeException uploadException = new RuntimeException("Upload failed");
        doThrow(uploadException).when(fileService).uploadFile(files.get(1), fileTypes.get(1), 1L);
                
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> productService.createProductWithFiles(testProductDTO, files, fileTypes));
        
        assertTrue(exception.getMessage().contains("Failed to upload file:"));
        verify(productRepository).deleteById(1L);
    }
    
    @Test
    public void createProductWithFiles_WithEmptyFileInList_ShouldSkipEmptyFile() {
        // Arrange
        MultipartFile emptyFile = new MockMultipartFile("empty", new byte[0]);
        List<MultipartFile> files = Arrays.asList(testMultipartFile, emptyFile);
        List<FileType> fileTypes = Arrays.asList(FileType.IMAGE, FileType.IMAGE);
        
        ProductDTO createdProductDTO = ProductDTO.builder().id(1L).build();
        
        when(mapper.toEntity(testProductDTO)).thenReturn(testProduct);
        when(productRepository.save(testProduct)).thenReturn(testProduct);
        when(mapper.toDto(testProduct)).thenReturn(createdProductDTO, testProductDTO);
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        
        // Act
        ProductDTO result = productService.createProductWithFiles(testProductDTO, files, fileTypes);
        
        // Assert
        assertNotNull(result);
        // Should only upload the non-empty file
        verify(fileService, times(1)).uploadFile(any(), any(), any());
        verify(fileService).uploadFile(testMultipartFile, FileType.IMAGE, 1L);
    }
    
    // Edge case tests
    @Test
    public void delete_WithNoFiles_ShouldDeleteProductWithoutCloudinaryCalls() {
        // Arrange
        Product productWithoutFiles = Product.builder()
                .id(3L)
                .name("Product without files")
                .build();
        
        when(productRepository.findById(3L)).thenReturn(Optional.of(productWithoutFiles));
        doNothing().when(productRepository).delete(productWithoutFiles);
        
        // Act
        productService.delete(3L);
        
        // Assert
        verify(productRepository).findById(3L);
        verify(cloudinaryService, never()).deleteFile(anyString(), anyString());
        verify(productRepository).delete(productWithoutFiles);
    }
    
}