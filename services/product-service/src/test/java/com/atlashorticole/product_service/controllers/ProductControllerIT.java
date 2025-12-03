package com.atlashorticole.product_service.controllers;

import com.atlashorticole.product_service.AbstractIntegrationTest;
import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class ProductControllerIT extends AbstractIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        productDTO = ProductDTO.builder()
                .name("Controller Product")
                .category(Category.BIOSTIMULANT)
                .description("Controller Description")
                .resume("Short resume")
                .composition("Chemical composition")
                .dosage("100ml per liter")
                .imageUrl("http://example.com/image.jpg")
                .technicalSheetUrl("http://example.com/tech-sheet.pdf")
                .displayOrder(1)
                .active(true)
                .build();
    }

    @Test
    void shouldCreateProduct() throws Exception {
        // When/Then
        MvcResult result = mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Controller Product"))
                .andExpect(jsonPath("$.category").value("BIOSTIMULANT"))
                .andExpect(jsonPath("$.active").value(true))
                .andExpect(header().exists("Location"))
                .andReturn();

        String location = result.getResponse().getHeader("Location");
        assertThat(location).contains("/api/products/");
    }

    @Test
    void shouldReturnBadRequestWhenCreatingInvalidProduct() throws Exception {
        // Given
        ProductDTO invalidDTO = ProductDTO.builder()
                .name("")  // Empty name should fail @NotBlank validation
                .category(null)  // Null category should fail @NotNull validation
                .build();

        // When/Then
        mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.category").exists());
    }

    @Test
    void shouldGetProductById() throws Exception {
        // Given
        MvcResult createResult = mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andReturn();

        ProductDTO created = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                ProductDTO.class
        );

        // When/Then
        mockMvc.perform(get("/api/products/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.name").value("Controller Product"));
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/products/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetAllProducts() throws Exception {
        // Given
        mockMvc.perform(post("/api/products/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)));

        // When/Then
        mockMvc.perform(get("/api/products/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name").exists());
    }

    @Test
    void shouldGetProductsByPage() throws Exception {
        // Given
        mockMvc.perform(post("/api/products/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)));

        // When/Then
        mockMvc.perform(get("/api/products/page")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(1)))
                .andExpect(jsonPath("$.pageable.pageNumber").value(0));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        // Given
        MvcResult createResult = mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andReturn();

        ProductDTO created = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                ProductDTO.class
        );

        ProductDTO updateDTO = ProductDTO.builder()
                .name("Updated Name")
                .category(Category.AMENDMENT)
                .description("Updated Description")
                .active(false)
                .build();

        // When/Then
        mockMvc.perform(put("/api/products/{id}", created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.category").value("AMENDMENT"))
                .andExpect(jsonPath("$.active").value(false));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        // Given
        MvcResult createResult = mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andReturn();

        ProductDTO created = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                ProductDTO.class
        );

        // When/Then
        mockMvc.perform(delete("/api/products/{id}", created.getId()))
                .andExpect(status().isNoContent());

        // Verify deletion
        mockMvc.perform(get("/api/products/{id}", created.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNoContentWhenDeletingNonExistingProduct() throws Exception {
        mockMvc.perform(delete("/api/products/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}