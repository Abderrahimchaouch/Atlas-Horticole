package com.atlashorticole.product_service.controllers;

import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.domain.FileType;
import com.atlashorticole.product_service.dto.FileDTO;
import com.atlashorticole.product_service.dto.ProductDTO;
import com.atlashorticole.product_service.services.FileService;
import com.atlashorticole.product_service.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private FileService fileService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/products";

    private ProductDTO testProductDTO;
    private FileDTO testFileDTO;

    @BeforeEach
    public void setUp() {
        testProductDTO = ProductDTO.builder()
                .id(1L)
                .name("Test Product")
                .category(Category.BIOSTIMULANT)
                .active(true)
                .build();

        testFileDTO = FileDTO.builder()
                .id(1L)
                .originalName("test.jpg")
                .fileType(FileType.IMAGE)
                .productId(1L)
                .build();
    }

    // ========== BASIC CRUD TESTS ==========

    @Test
    public void createProduct_ShouldCreateProduct() throws Exception {
        ProductDTO createDTO = ProductDTO.builder()
                .name("New Product")
                .category(Category.AMENDMENT)
                .build();

        ProductDTO createdDTO = ProductDTO.builder()
                .id(2L)
                .name("New Product")
                .category(Category.AMENDMENT)
                .build();

        when(productService.createNewProduct(any())).thenReturn(createdDTO);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(2)))
                .andExpect(jsonPath("$.name", equalTo("New Product")));

        verify(productService).createNewProduct(any());
    }

    @Test
    public void getProductById_ShouldReturnProduct() throws Exception {
        when(productService.findById(1L)).thenReturn(Optional.of(testProductDTO));

        mockMvc.perform(get(BASE_URL + "/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Test Product")));

        verify(productService).findById(1L);
    }

    @Test
    public void getProductById_NotFound_ShouldReturn404() throws Exception {
        when(productService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get(BASE_URL + "/{id}", 999L))
                .andExpect(status().isNotFound());

        verify(productService).findById(999L);
    }

    @Test
    public void getAllProducts_ShouldReturnProductList() throws Exception {
        when(productService.findALL()).thenReturn(List.of(testProductDTO));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", equalTo("Test Product")));

        verify(productService).findALL();
    }

    @Test
    public void updateProduct_ShouldUpdateProduct() throws Exception {
        ProductDTO updateDTO = ProductDTO.builder()
                .name("Updated Product")
                .category(Category.CORRECTOR)
                .build();

        ProductDTO updatedDTO = ProductDTO.builder()
                .id(1L)
                .name("Updated Product")
                .category(Category.CORRECTOR)
                .build();

        when(productService.updateProduct(1L, updateDTO)).thenReturn(updatedDTO);

        mockMvc.perform(put(BASE_URL + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Updated Product")));

        verify(productService).updateProduct(1L, updateDTO);
    }

    @Test
    public void deleteProduct_ShouldDeleteProduct() throws Exception {
        doNothing().when(productService).delete(1L);

        mockMvc.perform(delete(BASE_URL + "/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(productService).delete(1L);
    }

    // ========== BASIC FILE OPERATIONS ==========

    @Test
    public void uploadFileToProduct_ShouldUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "content".getBytes()
        );

        when(fileService.uploadFile(any(), eq(FileType.IMAGE), eq(1L)))
                .thenReturn(testFileDTO);

        mockMvc.perform(multipart(BASE_URL + "/{id}/upload-file", 1L)
                        .file(file)
                        .param("fileType", "IMAGE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalName", equalTo("test.jpg")));

        verify(fileService).uploadFile(any(), any(), eq(1L));
    }

    @Test
    public void getProductFiles_ShouldReturnFiles() throws Exception {
        when(fileService.getFilesByProductId(1L)).thenReturn(List.of(testFileDTO));

        mockMvc.perform(get(BASE_URL + "/{id}/files", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].originalName", equalTo("test.jpg")));

        verify(fileService).getFilesByProductId(1L);
    }

    @Test
    public void deleteProductFile_ShouldDeleteFile() throws Exception {
        doNothing().when(fileService).deleteFile(1L);

        mockMvc.perform(delete(BASE_URL + "/{id}/files/{fileId}", 1L, 1L))
                .andExpect(status().isNoContent());

        verify(fileService).deleteFile(1L);
    }

    // ========== BASIC PAGINATION ==========

    @Test
    public void getProductsByPage_ShouldReturnPaginatedProducts() throws Exception {
        Page<ProductDTO> page = new PageImpl<>(List.of(testProductDTO));
        when(productService.findByPage(any())).thenReturn(page);

        mockMvc.perform(get(BASE_URL + "/page")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));

        verify(productService).findByPage(any());
    }

    // ========== BASIC ERROR CASES ==========

    @Test
    public void createProduct_InvalidData_ShouldReturn400() throws Exception {
        ProductDTO invalidDTO = ProductDTO.builder().build(); // Missing required fields

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(productService, never()).createNewProduct(any());
    }

}