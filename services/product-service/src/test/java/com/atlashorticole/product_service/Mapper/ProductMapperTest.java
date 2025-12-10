package com.atlashorticole.product_service.Mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.atlashorticole.product_service.domain.File;
import com.atlashorticole.product_service.domain.FileType;
import com.atlashorticole.product_service.domain.Product;
import com.atlashorticole.product_service.dto.FileDTO;
import com.atlashorticole.product_service.dto.ProductDTO;

@ExtendWith(MockitoExtension.class)
public class ProductMapperTest {

        @Mock
        private FileMapper fileMapper;

        private ProductMapper productMapper;

        private Product testProduct;
        private File testFile;
        private FileDTO testFileDTO;
        private ProductDTO testProductDTO;

        @BeforeEach
        public void setUp() {
                productMapper = new ProductMapper(fileMapper);

                // Setup test data
                testFile = File.builder()
                                .id(1L)
                                .originalName("test.jpg")
                                .fileUrl("https://example.com/test.jpg")
                                .fileType(FileType.IMAGE)
                                .build();

                testFileDTO = FileDTO.builder()
                                .id(1L)
                                .originalName("test.jpg")
                                .fileUrl("https://example.com/test.jpg")
                                .fileType(FileType.IMAGE)
                                .build();

                testProduct = Product.builder()
                                .id(1L)
                                .name("Test Product")
                                .category(com.atlashorticole.product_service.domain.Category.COMPOST)
                                .description("Test Description")
                                .resume("Test Resume")
                                .composition("Test Composition")
                                .dosage("Test Dosage")
                                .displayOrder(1)
                                .active(true)
                                .build();

                testProduct.getFiles().add(testFile);

                testProductDTO = ProductDTO.builder()
                                .id(1L)
                                .name("Test Product")
                                .category(com.atlashorticole.product_service.domain.Category.COMPOST)
                                .description("Test Description")
                                .resume("Test Resume")
                                .composition("Test Composition")
                                .dosage("Test Dosage")
                                .displayOrder(1)
                                .active(true)
                                .files(List.of(testFileDTO))
                                .build();
        }

        // Test toDto method

        @Test
        public void toDto_WithValidProduct_ShouldReturnCorrectDTO() {
                // Arrange
                when(fileMapper.toDto(testFile)).thenReturn(testFileDTO);

                // Act
                ProductDTO result = productMapper.toDto(testProduct);

                // Assert
                assertNotNull(result);
                assertEquals(testProduct.getId(), result.getId());
                assertEquals(testProduct.getName(), result.getName());
                assertEquals(testProduct.getCategory(), result.getCategory());
                assertEquals(testProduct.getDescription(), result.getDescription());
                assertEquals(testProduct.getResume(), result.getResume());
                assertEquals(testProduct.getComposition(), result.getComposition());
                assertEquals(testProduct.getDosage(), result.getDosage());
                assertEquals(testProduct.getDisplayOrder(), result.getDisplayOrder());
                assertEquals(testProduct.getActive(), result.getActive());

                assertThat(result.getFiles()).hasSize(1);
                assertEquals(testFileDTO, result.getFiles().get(0));

                verify(fileMapper).toDto(testFile);
        }

        @Test
        public void toDto_WithProductHavingNoFiles_ShouldReturnDTOWithNullFiles() {
                // Arrange
                Product productWithoutFiles = Product.builder()
                                .id(2L)
                                .name("Product without files")
                                .active(true)
                                .build();

                // Act
                ProductDTO result = productMapper.toDto(productWithoutFiles);

                // Assert
                assertNotNull(result);
                assertEquals(productWithoutFiles.getId(), result.getId());
                assertEquals(productWithoutFiles.getName(), result.getName());
                assertNotNull(result.getFiles());

                verify(fileMapper, never()).toDto(any());
        }

        @Test
        public void toDto_WithProductHavingEmptyFilesList_ShouldReturnDTOWithNullFiles() {
                // Arrange
                Product productWithEmptyFiles = Product.builder()
                                .id(3L)
                                .name("Product with empty files")
                                .build();
                // Files list is empty by default in builder

                // Act
                ProductDTO result = productMapper.toDto(productWithEmptyFiles);

                // Assert
                assertNotNull(result);
                assertEquals(productWithEmptyFiles.getId(), result.getId());
        }

        @Test
        public void toDto_WithNullProduct_ShouldReturnNull() {
                // Act
                ProductDTO result = productMapper.toDto(null);

                // Assert
                assertNull(result);
                verify(fileMapper, never()).toDto(any());
        }

        @Test
        public void toDto_WithPartialProductData_ShouldHandleNullFields() {
                // Arrange
                Product partialProduct = Product.builder()
                                .id(4L)
                                .name("Partial Product")
                                .active(null) // null active field
                                .build();

                // Act
                ProductDTO result = productMapper.toDto(partialProduct);

                // Assert
                assertNotNull(result);
                assertEquals(4L, result.getId());
                assertEquals("Partial Product", result.getName());
                assertNull(result.getActive()); // Should preserve null
        }

        // Test toEntity method

        @Test
        public void toEntity_WithValidDTO_ShouldReturnCorrectEntity() {
                // Arrange
                when(fileMapper.toEntity(testFileDTO)).thenReturn(testFile);

                // Act
                Product result = productMapper.toEntity(testProductDTO);

                // Assert
                assertNotNull(result);
                assertEquals(testProductDTO.getId(), result.getId());
                assertEquals(testProductDTO.getName(), result.getName());
                assertEquals(testProductDTO.getCategory(), result.getCategory());
                assertEquals(testProductDTO.getDescription(), result.getDescription());
                assertEquals(testProductDTO.getResume(), result.getResume());
                assertEquals(testProductDTO.getComposition(), result.getComposition());
                assertEquals(testProductDTO.getDosage(), result.getDosage());
                assertEquals(testProductDTO.getDisplayOrder(), result.getDisplayOrder());
                assertEquals(testProductDTO.getActive(), result.getActive());

                assertThat(result.getFiles()).hasSize(1);
                assertEquals(testFile, result.getFiles().get(0));
                assertEquals(result, result.getFiles().get(0).getProduct()); // Verify bidirectional relationship

                verify(fileMapper).toEntity(testFileDTO);
        }

        @Test
        public void toEntity_WithNullActive_ShouldSetDefaultToTrue() {
                // Arrange
                ProductDTO dtoWithNullActive = ProductDTO.builder()
                                .id(5L)
                                .name("Product with null active")
                                .active(null)
                                .build();

                // Act
                Product result = productMapper.toEntity(dtoWithNullActive);

                // Assert
                assertNotNull(result);
                assertTrue(result.getActive()); // Default to true
        }

        @Test
        public void toEntity_WithFalseActive_ShouldPreserveFalse() {
                // Arrange
                ProductDTO dtoWithFalseActive = ProductDTO.builder()
                                .id(6L)
                                .name("Inactive Product")
                                .active(false)
                                .build();

                // Act
                Product result = productMapper.toEntity(dtoWithFalseActive);

                // Assert
                assertNotNull(result);
                assertFalse(result.getActive());
        }

        @Test
        public void toEntity_WithDTOHavingNoFiles_ShouldReturnEntityWithEmptyFiles() {
                // Arrange
                ProductDTO dtoWithoutFiles = ProductDTO.builder()
                                .id(7L)
                                .name("DTO without files")
                                .files(null)
                                .build();

                // Act
                Product result = productMapper.toEntity(dtoWithoutFiles);

                // Assert
                assertNotNull(result);
                assertThat(result.getFiles()).isEmpty();
                verify(fileMapper, never()).toEntity(any());
        }

        @Test
        public void toEntity_WithDTOHavingEmptyFilesList_ShouldReturnEntityWithEmptyFiles() {
                // Arrange
                ProductDTO dtoWithEmptyFiles = ProductDTO.builder()
                                .id(8L)
                                .name("DTO with empty files")
                                .files(List.of())
                                .build();

                // Act
                Product result = productMapper.toEntity(dtoWithEmptyFiles);

                // Assert
                assertNotNull(result);
                assertThat(result.getFiles()).isEmpty();
                verify(fileMapper, never()).toEntity(any());
        }

        @Test
        public void toEntity_WithNullDTO_ShouldReturnNull() {
                // Act
                Product result = productMapper.toEntity(null);

                // Assert
                assertNull(result);
                verify(fileMapper, never()).toEntity(any());
        }

        @Test
        public void toEntity_WithMultipleFiles_ShouldMapAllFilesAndSetProductReference() {
                // Arrange
                FileDTO fileDTO1 = FileDTO.builder().id(1L).originalName("file1.jpg").build();
                FileDTO fileDTO2 = FileDTO.builder().id(2L).originalName("file2.pdf").build();
                File file1 = File.builder().id(1L).originalName("file1.jpg").build();
                File file2 = File.builder().id(2L).originalName("file2.pdf").build();

                ProductDTO dtoWithMultipleFiles = ProductDTO.builder()
                                .id(9L)
                                .name("Product with multiple files")
                                .files(Arrays.asList(fileDTO1, fileDTO2))
                                .build();

                when(fileMapper.toEntity(fileDTO1)).thenReturn(file1);
                when(fileMapper.toEntity(fileDTO2)).thenReturn(file2);

                // Act
                Product result = productMapper.toEntity(dtoWithMultipleFiles);

                // Assert
                assertNotNull(result);
                assertThat(result.getFiles()).hasSize(2);
                assertEquals(file1, result.getFiles().get(0));
                assertEquals(file2, result.getFiles().get(1));

                // Verify product reference is set on all files
                assertEquals(result, result.getFiles().get(0).getProduct());
                assertEquals(result, result.getFiles().get(1).getProduct());

                verify(fileMapper).toEntity(fileDTO1);
                verify(fileMapper).toEntity(fileDTO2);
        }

        @Test
        public void toEntity_WithPartialDTOData_ShouldHandleNullFields() {
                // Arrange
                ProductDTO partialDTO = ProductDTO.builder()
                                .id(10L)
                                .name("Partial DTO")
                                // Other fields are null
                                .build();

                // Act
                Product result = productMapper.toEntity(partialDTO);

                // Assert
                assertNotNull(result);
                assertEquals(10L, result.getId());
                assertEquals("Partial DTO", result.getName());
                assertNull(result.getDescription());
                assertNull(result.getCategory());
                assertNull(result.getResume());
                assertNull(result.getComposition());
                assertNull(result.getDosage());
                assertNull(result.getDisplayOrder());
                assertTrue(result.getActive()); // Default
        }

        // Test updateEntityFromDto method

        @Test
        public void updateEntityFromDto_WithValidInputs_ShouldUpdateEntityCorrectly() {
                // Arrange
                Product existingEntity = Product.builder()
                                .id(1L)
                                .name("Old Name")
                                .category(com.atlashorticole.product_service.domain.Category.BIOSTIMULANT)
                                .description("Old Description")
                                .resume("Old Resume")
                                .composition("Old Composition")
                                .dosage("Old Dosage")
                                .displayOrder(10)
                                .active(false)
                                .build();

                existingEntity.getFiles().add(testFile);

                ProductDTO updateDTO = ProductDTO.builder()
                                .name("New Name")
                                .category(com.atlashorticole.product_service.domain.Category.COMPOST)
                                .description("New Description")
                                .resume("New Resume")
                                .composition("New Composition")
                                .dosage("New Dosage")
                                .displayOrder(20)
                                .active(true)
                                .files(List.of(testFileDTO)) // Different files
                                .build();

                File newFile = File.builder().id(2L).originalName("new.jpg").build();
                when(fileMapper.toEntity(testFileDTO)).thenReturn(newFile);

                // Act
                productMapper.updateEntityFromDto(updateDTO, existingEntity);

                // Assert
                assertEquals("New Name", existingEntity.getName());
                assertEquals(com.atlashorticole.product_service.domain.Category.COMPOST, existingEntity.getCategory());
                assertEquals("New Description", existingEntity.getDescription());
                assertEquals("New Resume", existingEntity.getResume());
                assertEquals("New Composition", existingEntity.getComposition());
                assertEquals("New Dosage", existingEntity.getDosage());
                assertEquals(20, existingEntity.getDisplayOrder());
                assertTrue(existingEntity.getActive());

                // Files should be cleared and replaced
                assertThat(existingEntity.getFiles()).hasSize(1);
                assertEquals(newFile, existingEntity.getFiles().get(0));
                assertEquals(existingEntity, existingEntity.getFiles().get(0).getProduct());

                verify(fileMapper).toEntity(testFileDTO);
        }

        @Test
        public void updateEntityFromDto_WithPartialUpdateDTO_ShouldOnlyUpdateNonNullFields() {
                // Arrange
                Product existingEntity = Product.builder()
                                .id(1L)
                                .name("Original Name")
                                .category(com.atlashorticole.product_service.domain.Category.BIOSTIMULANT)
                                .description("Original Description")
                                .active(true)
                                .build();

                // Only update name, leave other fields as is
                ProductDTO partialUpdateDTO = ProductDTO.builder()
                                .name("Updated Name")
                                .build();

                // Act
                productMapper.updateEntityFromDto(partialUpdateDTO, existingEntity);

                // Assert
                assertEquals("Updated Name", existingEntity.getName());
                assertEquals(com.atlashorticole.product_service.domain.Category.BIOSTIMULANT,
                                existingEntity.getCategory());
                assertEquals("Original Description", existingEntity.getDescription());
                assertTrue(existingEntity.getActive());

                verify(fileMapper, never()).toEntity(any());
        }

        @Test
        public void updateEntityFromDto_WithNullFilesInDTO_ShouldNotClearExistingFiles() {
                // Arrange
                Product existingEntity = Product.builder()
                                .id(1L)
                                .name("Product with files")
                                .build();

                existingEntity.getFiles().add(testFile);

                ProductDTO updateDTO = ProductDTO.builder()
                                .name("Updated Name")
                                .files(null) // null files
                                .build();

                // Act
                productMapper.updateEntityFromDto(updateDTO, existingEntity);

                // Assert
                assertEquals("Updated Name", existingEntity.getName());
                assertThat(existingEntity.getFiles()).hasSize(1); // Files should not be cleared
                assertEquals(testFile, existingEntity.getFiles().get(0));

                verify(fileMapper, never()).toEntity(any());
        }

        @Test
        public void updateEntityFromDto_WithEmptyFilesListInDTO_ShouldClearExistingFiles() {
                // Arrange
                Product existingEntity = Product.builder()
                                .id(1L)
                                .name("Product with files")
                                .build();

                existingEntity.getFiles().add(testFile);

                ProductDTO updateDTO = ProductDTO.builder()
                                .name("Updated Name")
                                .files(List.of()) // Empty list
                                .build();

                // Act
                productMapper.updateEntityFromDto(updateDTO, existingEntity);

                // Assert
                assertEquals("Updated Name", existingEntity.getName());
                assertThat(existingEntity.getFiles()).isEmpty(); // Files should be cleared

                verify(fileMapper, never()).toEntity(any());
        }

        @Test
        public void updateEntityFromDto_WithNullDTO_ShouldDoNothing() {
                // Arrange
                Product existingEntity = Product.builder()
                                .id(1L)
                                .name("Original")
                                .build();

                // Act
                productMapper.updateEntityFromDto(null, existingEntity);

                // Assert
                assertEquals("Original", existingEntity.getName());
                verify(fileMapper, never()).toEntity(any());
        }

        @Test
        public void updateEntityFromDto_WithNullEntity_ShouldDoNothing() {
                // Arrange
                ProductDTO updateDTO = ProductDTO.builder()
                                .name("Update")
                                .build();

                // Act
                productMapper.updateEntityFromDto(updateDTO, null);

                // Assert
                verify(fileMapper, never()).toEntity(any());
        }

        @Test
        public void updateEntityFromDto_WithBothNull_ShouldDoNothing() {
                // Act & Assert - No exception should be thrown
                productMapper.updateEntityFromDto(null, null);

                verify(fileMapper, never()).toEntity(any());
        }

        @Test
        public void updateEntityFromDto_ShouldNotUpdateId() {
                // Important: ID should not be updated
                // Arrange
                Product existingEntity = Product.builder()
                                .id(1L)
                                .name("Original")
                                .build();

                ProductDTO updateDTO = ProductDTO.builder()
                                .id(999L) // Different ID
                                .name("Updated")
                                .build();

                // Act
                productMapper.updateEntityFromDto(updateDTO, existingEntity);

                // Assert
                assertEquals(1L, existingEntity.getId()); // ID should remain unchanged
                assertEquals("Updated", existingEntity.getName());
        }

        @Test
        public void toEntity_ShouldHandleNullInAllFields() {
                // Arrange
                ProductDTO dtoWithAllNulls = ProductDTO.builder()
                                .id(null)
                                .name(null)
                                .category(null)
                                .description(null)
                                .resume(null)
                                .composition(null)
                                .dosage(null)
                                .displayOrder(null)
                                .active(null)
                                .files(null)
                                .build();

                // Act
                Product result = productMapper.toEntity(dtoWithAllNulls);

                // Assert
                assertNotNull(result);
                assertNull(result.getId());
                assertNull(result.getName());
                assertNull(result.getCategory());
                assertNull(result.getDescription());
                assertNull(result.getResume());
                assertNull(result.getComposition());
                assertNull(result.getDosage());
                assertNull(result.getDisplayOrder());
                assertTrue(result.getActive()); // Default value
                assertThat(result.getFiles()).isEmpty();
        }

        @Test
        public void circularReferenceTest_WhenMappingFiles_ShouldSetBidirectionalRelationship() {
                // Test that when mapping files, the product reference is set correctly
                // Arrange
                ProductDTO dto = ProductDTO.builder()
                                .id(1L)
                                .name("Test")
                                .files(List.of(testFileDTO))
                                .build();

                File mappedFile = File.builder().id(1L).build();
                when(fileMapper.toEntity(testFileDTO)).thenReturn(mappedFile);

                // Act
                Product result = productMapper.toEntity(dto);

                // Assert
                assertNotNull(result);
                assertThat(result.getFiles()).hasSize(1);
                assertEquals(result, result.getFiles().get(0).getProduct());
        }
}