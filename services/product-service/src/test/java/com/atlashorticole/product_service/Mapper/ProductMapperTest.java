 package com.atlashorticole.product_service.Mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.domain.Product;
import com.atlashorticole.product_service.dto.ProductDTO;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productMapper = new ProductMapper();
    }

    @Test
    void testToDto() {
        // Arrange
        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .category(Category.BIOSTIMULANT)
                .resume("Test Resume")
                .composition("Test Composition")
                .dosage("10ml")
                .imageUrl("http://example.com/image.jpg")
                .technicalSheetUrl("http://example.com/sheet.pdf")
                .build();

        // Act
        ProductDTO dto = productMapper.toDto(product);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Test Product", dto.getName());
        assertEquals("Test Description", dto.getDescription());
        assertEquals(Category.BIOSTIMULANT, dto.getCategory());
    }

    @Test
    void testToEntity() {
     
        ProductDTO dto = ProductDTO.builder()
               .id(1L)
                .name("Test Product")
                .description("Test Description")
                .category(Category.BIOSTIMULANT)
                .resume("Test Resume")
                .composition("Test Composition")
                .dosage("10ml")
                .imageUrl("http://example.com/image.jpg")
                .technicalSheetUrl("http://example.com/sheet.pdf")
                .build();

  
        Product product = productMapper.toEntity(dto);

    
        assertNotNull(product);
        assertEquals("Test Product", product.getName());
        assertEquals("Test Description", product.getDescription());
        assertEquals(Category.BIOSTIMULANT, product.getCategory());
    }

    @Test
    void testUpdateEntityFromDto() {
    
        Product existingProduct = Product.builder()
                .id(1L)
                .name("Old Name")
                .description("Old Description")
                .category(Category.AMENDMENT)
                .build();

        ProductDTO updateDto = ProductDTO.builder()
                .name("New Name")
                .description("New Description")
                .category(Category.BIOSTIMULANT)
                .build();


        productMapper.updateEntityFromDto(updateDto, existingProduct);

 
        assertEquals("New Name", existingProduct.getName());
        assertEquals("New Description", existingProduct.getDescription());
        assertEquals(Category.BIOSTIMULANT, existingProduct.getCategory());
        assertEquals(1L, existingProduct.getId()); // ID should not change
    }

    @Test
    void testToDto_WithNullProduct() {
     
        assertNull(productMapper.toDto(null));
    }

    @Test
    void testToEntity_WithNullDto() {
      
        assertNull(productMapper.toEntity(null));
    }
}
