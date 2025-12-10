package com.atlashorticole.product_service.Mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.atlashorticole.product_service.domain.File;
import com.atlashorticole.product_service.domain.FileType;
import com.atlashorticole.product_service.domain.Product;
import com.atlashorticole.product_service.dto.FileDTO;

class FileMapperTest {

    private final FileMapper fileMapper = new FileMapper();

    @Test
    void toDto_WithValidFile_ShouldMapAllFieldsCorrectly() {
        // Arrange
        Product product = Product.builder().id(100L).name("Test Product").build();

        File file = File.builder()
                .id(1L)
                .originalName("test.pdf")
                .fileUrl("https://example.com/test.pdf")
                .publicId("public_123")
                .size(1024L)
                .fileType(FileType.PDF)
                .product(product)
                .build();

        // Act
        FileDTO result = fileMapper.toDto(file);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test.pdf", result.getOriginalName());
        assertEquals("https://example.com/test.pdf", result.getFileUrl());
        assertEquals("public_123", result.getPublicId());
        assertEquals(1024L, result.getSize());
        assertEquals(FileType.PDF, result.getFileType());
        assertEquals(100L, result.getProductId());
    }

    @Test
    void toDto_WithFileWithoutProduct_ShouldMapProductIdAsNull() {
        // Arrange
        File file = File.builder()
                .id(1L)
                .originalName("test.jpg")
                .fileType(FileType.IMAGE)
                .product(null) // No product
                .build();

        // Act
        FileDTO result = fileMapper.toDto(file);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertNull(result.getProductId());
    }

    @Test
    void toDto_WithNullFile_ShouldReturnNull() {
        // Act & Assert
        assertNull(fileMapper.toDto(null));
    }

    @Test
    void toEntity_WithValidDTO_ShouldMapAllFieldsCorrectly() {
        // Arrange
        FileDTO dto = FileDTO.builder()
                .id(1L)
                .originalName("test.pdf")
                .fileUrl("https://example.com/test.pdf")
                .publicId("public_123")
                .size(1024L)
                .fileType(FileType.PDF)
                .productId(100L)
                .build();

        // Act
        File result = fileMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test.pdf", result.getOriginalName());
        assertEquals("https://example.com/test.pdf", result.getFileUrl());
        assertEquals("public_123", result.getPublicId());
        assertEquals(1024L, result.getSize());
        assertEquals(FileType.PDF, result.getFileType());
        // Note: product is NOT set in toEntity (as per your implementation)
    }

    @Test
    void toEntity_WithNullDTO_ShouldReturnNull() {
        // Act & Assert
        assertNull(fileMapper.toEntity(null));
    }

    @Test
    void toEntity_WithPartialDTO_ShouldHandleNullFields() {
        // Arrange
        FileDTO dto = FileDTO.builder()
                .id(1L)
                .originalName("test.jpg")
                .fileType(FileType.IMAGE)
                // Other fields are null
                .build();

        // Act
        File result = fileMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("test.jpg", result.getOriginalName());
        assertEquals(FileType.IMAGE, result.getFileType());
        assertNull(result.getFileUrl());
        assertNull(result.getPublicId());
        assertNull(result.getSize());
    }

    // Test both directions for consistency (optional but good practice)
    @Test
    void toDtoAndToEntity_ShouldBeConsistent() {
        // Arrange
        File originalFile = File.builder()
                .id(1L)
                .originalName("test.pdf")
                .fileUrl("https://example.com/test.pdf")
                .fileType(FileType.PDF)
                .size(500L)
                .build();

        // Act
        FileDTO dto = fileMapper.toDto(originalFile);
        File entity = fileMapper.toEntity(dto);

        // Assert - Compare fields that are mapped both ways
        assertEquals(originalFile.getId(), entity.getId());
        assertEquals(originalFile.getOriginalName(), entity.getOriginalName());
        assertEquals(originalFile.getFileUrl(), entity.getFileUrl());
        assertEquals(originalFile.getFileType(), entity.getFileType());
        assertEquals(originalFile.getSize(), entity.getSize());
    }
}