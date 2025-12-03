package com.atlashorticole.product_service.controllers;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.dto.ProductDTO;
import com.atlashorticole.product_service.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASED_URL = "/api/products/";

    private ProductDTO testProductDTO;
    private ProductDTO testProductDTO2;
    private List<ProductDTO> products;
    @BeforeEach
    void setUp() {
        products = new ArrayList<>();
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
        testProductDTO2 = ProductDTO.builder()
                .id(2L)
                .name("Test Product2")
                .description("Test Description2")
                .category(Category.BIOSTIMULANT)
                .imageUrl("http://example.com/image2.jpg")
                .technicalSheetUrl("http://example.com/sheet2.pdf")
                .active(false)
                .displayOrder(2)
                .build();
                        
        products.add(testProductDTO);
        products.add(testProductDTO2);
    }

    @Test
    void testGetByPageProduct_Success() throws Exception {
        // Arrange


        Page<ProductDTO> page = new PageImpl<>(products);
        when(productService.findByPage(any())).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get(BASED_URL+"page")
                .param("page", "0")
                .param("size", "20")
                .param("sort", "id,asc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].name", equalTo("Test Product")));

        verify(productService, times(1)).findByPage(any());
    }

    @Test
    void testGetAllProduct_Success() throws Exception {
        //arrange
        when(productService.findALL()).thenReturn(products);

        mockMvc.perform(get(BASED_URL+"api"))
                        .andExpect(status().isOk())        
                        .andExpect(jsonPath("$.content", hasSize(2)));
        
        verify(productService,times(2)).findALL();

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
    public void testGetProductById_Success() throws Exception {
    // Arrange
    Long productId = 1L;
    ProductDTO expectedProduct = ProductDTO.builder()
            .id(productId)
            .name("Test product")
            .category(Category.BIOSTIMULANT)
            .build();
    
    when(productService.findById(productId)).thenReturn(Optional.of(expectedProduct));

    // Act & Assert
    mockMvc.perform(get(BASED_URL + "/{id}", productId))  // Meilleure pratique avec placeholder
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", equalTo(productId.intValue())))
            .andExpect(jsonPath("$.name", equalTo("Test product")))
            .andExpect(jsonPath("$.category", equalTo("BIOSTIMULANT")));

    // Verify
    verify(productService, times(1)).findById(productId);
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
    // Arrange
    Long nonExistentId = 999L;
    when(productService.findById(nonExistentId)).thenReturn(Optional.empty());

    // Act & Assert
    mockMvc.perform(get(BASED_URL + "/{id}", nonExistentId))
            .andExpect(status().isNotFound());

    // Verify
    verify(productService, times(1)).findById(nonExistentId);
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
    @Test
    public void testDeleteProduct_DataIntegrityViolation() throws Exception {
        // Arrange
        Long productId = 1L;
        
        // Simule une violation de contrainte (ex: produit référencé ailleurs)
        doThrow(new DataIntegrityViolationException(""))
            .when(productService).delete(productId);

        // Act & Assert
        mockMvc.perform(delete(BASED_URL + "/{id}", productId))
                .andExpect(status().isConflict()) // 409 Conflict
                .andExpect(jsonPath("$.error").value(containsString("Database constraint violated")));

        verify(productService, times(1)).delete(productId);
    }
    @Test
    public void testDeleteProduct_NotFound() throws Exception {
        // Arrange
        Long nonExistentId = 999L;

    
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found with ID: "+nonExistentId))
                .when(productService).delete(nonExistentId);

        // Act & Assert
        mockMvc.perform(delete(BASED_URL + "/{id}", nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error",equalTo("Product not found with ID: "+nonExistentId)));

        verify(productService, times(1)).delete(nonExistentId);
    }
}
