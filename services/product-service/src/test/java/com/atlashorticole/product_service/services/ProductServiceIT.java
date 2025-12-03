package com.atlashorticole.product_service.services;

import com.atlashorticole.product_service.AbstractIntegrationTest;
import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.dto.ProductDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductServiceIT extends AbstractIntegrationTest {

    @Autowired
    private ProductService productService;

    private ProductDTO productDTO1;
    private ProductDTO productDTO2;

    @BeforeEach
    void setUp() {
        productDTO1 = ProductDTO.builder()
                .name("Service Product 1")
                .category(Category.BIOSTIMULANT)
                .description("Service Description 1")
                .active(true)
                .displayOrder(1)
                .build();

        productDTO2 = ProductDTO.builder()
                .name("Service Product 2")
                .category(Category.FERTILIZER_WATER_SOLUBLE)
                .description("Service Description 2")
                .active(false)
                .displayOrder(2)
                .build();
    }

    @Test
    void shouldCreateNewProduct() {
        // When
        ProductDTO created = productService.createNewProduct(productDTO1);

        // Then
        assertThat(created.getId()).isNotNull();
        assertThat(created.getName()).isEqualTo("Service Product 1");
        assertThat(created.getCategory()).isEqualTo(Category.BIOSTIMULANT);
    }

    @Test
    void shouldFindProductById() {
        // Given
        ProductDTO created = productService.createNewProduct(productDTO1);

        // When
        Optional<ProductDTO> found = productService.findById(created.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Service Product 1");
    }

    @Test
    void shouldReturnEmptyWhenProductNotFound() {
        // When
        Optional<ProductDTO> found = productService.findById(999L);

        // Then
        assertThat(found).isEmpty();
    }

    @Test
    void shouldFindAllProducts() {
        // Given
        productService.createNewProduct(productDTO1);
        productService.createNewProduct(productDTO2);

        // When
        List<ProductDTO> products = productService.findALL();

        // Then
        assertThat(products).hasSize(2);
    }

    @Test
    void shouldFindProductsByPage() {
        // Given
        productService.createNewProduct(productDTO1);
        productService.createNewProduct(productDTO2);
        Pageable pageable = PageRequest.of(0, 10, Sort.by("name").ascending());

        // When
        Page<ProductDTO> page = productService.findByPage(pageable);

        // Then
        assertThat(page.getContent()).hasSize(2);
        assertThat(page.getTotalElements()).isEqualTo(2);
    }

    @Test
    void shouldFindProductsByCategory() {
        // Given
        productService.createNewProduct(productDTO1);
        productService.createNewProduct(productDTO2);
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<ProductDTO> page = productService.findByCategory(Category.BIOSTIMULANT, pageable);

        // Then
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getCategory()).isEqualTo(Category.BIOSTIMULANT);
    }

    @Test
    void shouldUpdateProduct() {
        // Given
        ProductDTO created = productService.createNewProduct(productDTO1);
        ProductDTO updateDTO = ProductDTO.builder()
                .name("Updated Name")
                .category(Category.AMENDMENT)
                .description("Updated Description")
                .active(false)
                .displayOrder(99)
                .build();

        // When
        ProductDTO updated = productService.updateProduct(created.getId(), updateDTO);

        // Then
        assertThat(updated.getId()).isEqualTo(created.getId());
        assertThat(updated.getName()).isEqualTo("Updated Name");
        assertThat(updated.getCategory()).isEqualTo(Category.AMENDMENT);
        assertThat(updated.getDescription()).isEqualTo("Updated Description");
        assertThat(updated.getActive()).isFalse();
        assertThat(updated.getDisplayOrder()).isEqualTo(99);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingProduct() {
        // Given
        ProductDTO updateDTO = ProductDTO.builder()
                .name("Updated Name")
                .category(Category.AMENDMENT)
                .build();

        // When/Then
        assertThatThrownBy(() -> productService.updateProduct(999L, updateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Product not found");
    }

    @Test
    void shouldDeleteProduct() {
        // Given
        ProductDTO created = productService.createNewProduct(productDTO1);

        // When
        productService.delete(created.getId());

        // Then
        Optional<ProductDTO> found = productService.findById(created.getId());
        assertThat(found).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {
        // When/Then
        assertThatThrownBy(() -> productService.delete(999L))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Product not found");
    }
}