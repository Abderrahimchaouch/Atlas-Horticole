package com.atlashorticole.product_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.atlashorticole.product_service.Mapper.FileMapper;
import com.atlashorticole.product_service.Mapper.ProductMapper;
import com.atlashorticole.product_service.domain.File;
import com.atlashorticole.product_service.domain.FileType;
import com.atlashorticole.product_service.domain.Product;
import com.atlashorticole.product_service.dto.FileDTO;
import com.atlashorticole.product_service.repository.FileRepository;
import com.atlashorticole.product_service.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private FileMapper fileMapper;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductService productService;

    @InjectMocks
    private FileServiceImpl fileService;

    private Product testProduct;
    private File testFile;
    private FileDTO testFileDTO;
    private MultipartFile testMultipartFile;

    @BeforeEach
    public void setUp() {
        // Setup test data
        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .build();

        testFile = File.builder()
                .id(1L)
                .originalName("test.pdf")
                .fileUrl("https://cloudinary.com/test.pdf")
                .publicId("test_public_id")
                .size(1024L)
                .fileType(FileType.PDF)
                .product(testProduct)
                .build();

        testFileDTO = FileDTO.builder()
                .id(1L)
                .originalName("test.pdf")
                .fileUrl("https://cloudinary.com/test.pdf")
                .publicId("test_public_id")
                .size(1024L)
                .fileType(FileType.PDF)
                .productId(1L)
                .build();

        testMultipartFile = new MockMultipartFile(
                "file",
                "test.pdf",
                "application/pdf",
                "Test PDF Content".getBytes());
    }

    @Test
    public void uploadFile_WithValidPdfAndProduct_ShouldReturnFileDTO() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(cloudinaryService.uploadPdf(any(MultipartFile.class))).thenReturn("https://cloudinary.com/test.pdf");
        when(fileRepository.save(any(File.class))).thenReturn(testFile);
        when(fileMapper.toDto(testFile)).thenReturn(testFileDTO);

        // Act
        FileDTO result = fileService.uploadFile(testMultipartFile, FileType.PDF, 1L);

        // Assert
        assertNotNull(result);
        assertEquals("test.pdf", result.getOriginalName());
        assertEquals(FileType.PDF, result.getFileType());

        verify(productRepository).findById(1L);
        verify(cloudinaryService).uploadPdf(testMultipartFile);
        verify(fileRepository).save(any(File.class));
        verify(fileMapper).toDto(testFile);
    }

    @Test
    public void uploadFile_WithValidImage_ShouldUploadImageToCloudinary() {
        // Arrange
        MultipartFile imageFile = new MockMultipartFile(
                "image",
                "test.jpg",
                "image/jpeg",
                "Test Image Content".getBytes());

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(cloudinaryService.uploadImage(any(MultipartFile.class))).thenReturn("https://cloudinary.com/test.jpg");
        when(fileRepository.save(any(File.class))).thenReturn(testFile);
        when(fileMapper.toDto(testFile)).thenReturn(testFileDTO);

        // Act
        fileService.uploadFile(imageFile, FileType.IMAGE, 1L);

        // Assert
        verify(cloudinaryService).uploadImage(imageFile);
        verify(cloudinaryService, never()).uploadPdf(any());
    }

    @Test
    public void uploadFile_WhenProductNotFound_ShouldThrowException() {
        // Arrange
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> fileService.uploadFile(testMultipartFile, FileType.PDF, 999L));

        assertEquals("Product not found with id: 999", exception.getMessage());

        verify(fileRepository, never()).save(any());
        verify(cloudinaryService, never()).uploadPdf(any());
    }

    @Test
    public void deleteFile_WithValidId_ShouldDeleteFromRepositoryAndCloudinary() {
        // Arrange
        when(fileRepository.findById(1L)).thenReturn(Optional.of(testFile));

        // Act
        fileService.deleteFile(1L);

        // Assert
        verify(fileRepository).findById(1L);
        verify(cloudinaryService).deleteFile("test_public_id", "raw");
        verify(fileRepository).delete(testFile);
    }

    @Test
    public void deleteFile_WhenFileNotFound_ShouldThrowException() {
        // Arrange
        when(fileRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> fileService.deleteFile(999L));

        verify(cloudinaryService, never()).deleteFile(anyString(), anyString());
        verify(fileRepository, never()).delete(any());
    }

    @Test
    public void deleteFile_WithImageType_ShouldUseImageResourceType() {
        // Arrange
        File imageFile = File.builder()
                .id(2L)
                .fileType(FileType.IMAGE)
                .publicId("image_public_id")
                .build();

        when(fileRepository.findById(2L)).thenReturn(Optional.of(imageFile));

        // Act
        fileService.deleteFile(2L);

        // Assert
        verify(cloudinaryService).deleteFile("image_public_id", "image");
    }

    @Test
    public void getFilesByProductId_ShouldReturnListOfFileDTOs() {
        // Arrange
        List<File> files = List.of(testFile);
        when(fileRepository.findByProductId(1L)).thenReturn(files);
        when(fileMapper.toDto(testFile)).thenReturn(testFileDTO);

        // Act
        List<FileDTO> result = fileService.getFilesByProductId(1L);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(fileRepository).findByProductId(1L);
        verify(fileMapper).toDto(testFile);
    }

    @Test
    public void getFilesByProductIdAndType_ShouldReturnFilteredFileDTOs() {
        // Arrange
        List<File> pdfFiles = List.of(testFile);
        when(fileRepository.findByProductIdAndFileType(1L, FileType.PDF)).thenReturn(pdfFiles);
        when(fileMapper.toDto(testFile)).thenReturn(testFileDTO);

        // Act
        List<FileDTO> result = fileService.getFilesByProductIdAndType(1L, FileType.PDF);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFileType()).isEqualTo(FileType.PDF);
        verify(fileRepository).findByProductIdAndFileType(1L, FileType.PDF);
    }

    @Test
    public void getAllFiles_ShouldReturnAllFileDTOs() {
        // Arrange
        List<File> files = List.of(testFile);
        when(fileRepository.findAll()).thenReturn(files);
        when(fileMapper.toDto(testFile)).thenReturn(testFileDTO);

        // Act
        List<FileDTO> result = fileService.getAllFiles();

        // Assert
        assertThat(result).hasSize(1);
        verify(fileRepository).findAll();
        verify(fileMapper).toDto(testFile);
    }
}