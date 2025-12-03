package com.atlashorticole.product_service.repository;

import com.atlashorticole.product_service.AbstractIntegrationTest;
import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ProductRepositoryIT extends AbstractIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveAndFindProduct() {
        // Given
        Product product = Product.builder()
                .name("Test Product")
                .category(Category.BIOSTIMULANT)
                .active(true)
                .build();

        // When
        Product saved = productRepository.save(product);
        Optional<Product> found = productRepository.findById(saved.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test Product");
        assertThat(found.get().getCategory()).isEqualTo(Category.BIOSTIMULANT);
    }

    @Test
    void shouldFindByCategory() {
        // Given
        Product product1 = Product.builder()
                .name("BioStimulant")
                .category(Category.BIOSTIMULANT)
                .build();
        
        Product product2 = Product.builder()
                .name("Fertilizer")
                .category(Category.FERTILIZER_WATER_SOLUBLE)
                .build();
        
        productRepository.save(product1);
        productRepository.save(product2);

        // When
        var page = productRepository.findByCategory(
            Category.BIOSTIMULANT, 
            PageRequest.of(0, 10)
        );

        // Then
        assertThat(page.getContent()).hasSize(1);
        assertThat(page.getContent().get(0).getName()).isEqualTo("BioStimulant");
    }

    @Test
    void shouldUpdateProduct() {
        // Given
        Product product = Product.builder()
                .name("Original Name")
                .category(Category.BIOSTIMULANT)
                .build();
        
        Product saved = productRepository.save(product);
        saved.setName("Updated Name");

        // When
        Product updated = productRepository.save(saved);

        // Then
        assertThat(updated.getName()).isEqualTo("Updated Name");
    }

    @Test
    void shouldDeleteProduct() {
        // Given
        Product product = Product.builder()
                .name("To Delete")
                .category(Category.BIOSTIMULANT)
                .build();
        
        Product saved = productRepository.save(product);

        // When
        productRepository.deleteById(saved.getId());

        // Then
        Optional<Product> found = productRepository.findById(saved.getId());
        assertThat(found).isEmpty();
    }
}