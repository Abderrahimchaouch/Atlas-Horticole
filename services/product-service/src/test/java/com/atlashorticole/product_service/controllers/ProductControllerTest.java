package com.atlashorticole.product_service.controllers;
/* 
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.dto.ProductDTO;
import com.atlashorticole.product_service.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductDTO testProductDTO;

    @BeforeEach
    void setUp() {
        testProductDTO = ProductDTO.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .category(Category.BIOSTIMULANT)
                .imageUrl("http://example.com/image.jpg")
                .technicalSheetUrl("http://example.com/sheet.pdf")
                .active(true)
                .displayOrder(1)
                .build();
    }

    @Test
    void testGetByPageProduct_Success() throws Exception {
        // Arrange
        Page<ProductDTO> page = new PageImpl<>(Arrays.asList(testProductDTO));
        when(productService.findByPage(any())).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/products/page")
                .param("page", "0")
                .param("size", "20")
                .param("sort", "id,asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", equalTo("Test Product")));

        verify(productService, times(1)).findByPage(any());
    }

    @Test
    void testGetAllProduct_Success() throws Exception {
        // Arrange
        List<ProductDTO> products = Arrays.asList(testProductDTO);
        when(productService.findALL()).thenReturn(products);

        // Act & Assert
        mockMvc.perform(get("/api/products/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("Test Product")));

        verify(productService, times(1)).findALL();
    }

    @Test
    void testGetProductById_Success() throws Exception {
        // Arrange
        when(productService.findById(1L)).thenReturn(Optional.of(testProductDTO));

        // Act & Assert
        mockMvc.perform(get("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Test Product")))
                .andExpect(jsonPath("$.id", equalTo(1)));

        verify(productService, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        // Arrange
        when(productService.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/products/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).findById(999L);
    }

    @Test
    void testCreateProduct_Success() throws Exception {
        // Arrange
        ProductDTO createDTO = ProductDTO.builder()
                .name("New Product")
                .category(Category.AMENDMENT)
                .description("New Description")
                .build();

        ProductDTO createdDTO = ProductDTO.builder()
                .id(2L)
                .name("New Product")
                .category(Category.AMENDMENT)
                .description("New Description")
                .build();

        when(productService.createNewProduct(any())).thenReturn(createdDTO);

        // Act & Assert
        mockMvc.perform(post("/api/products/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(2)))
                .andExpect(jsonPath("$.name", equalTo("New Product")));

        verify(productService, times(1)).createNewProduct(any());
    }

    @Test
    void testCreateProduct_InvalidData() throws Exception {
        // Arrange
        ProductDTO invalidDTO = ProductDTO.builder()
                .description("No name")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/products/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(productService, never()).createNewProduct(any());
    }

    @Test
    void testUpdateProduct_Success() throws Exception {
        // Arrange
        ProductDTO updateDTO = ProductDTO.builder()
                .name("Updated Product")
                .category(Category.CORRECTOR)
                .description("Updated Description")
                .build();

        ProductDTO updatedDTO = ProductDTO.builder()
                .id(1L)
                .name("Updated Product")
                .category(Category.CORRECTOR)
                .description("Updated Description")
                .build();

        when(productService.updateProduct(1L, updateDTO)).thenReturn(updatedDTO);

        // Act & Assert
        mockMvc.perform(put("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Updated Product")));

        verify(productService, times(1)).updateProduct(1L, updateDTO);
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        // Arrange
        doNothing().when(productService).delete(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).delete(1L);
    }
}
*/