package com.atlashorticole.product_service.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.atlashorticole.product_service.Mapper.ProductMapper;
import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.domain.Product;
import com.atlashorticole.product_service.dto.ProductDTO;
import com.atlashorticole.product_service.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

  
    private ProductMapper mapper ;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;
    private ProductDTO testProductDTO;

    @BeforeEach
    void setUp() {
        mapper = new ProductMapper();
        testProduct = Product.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .category(Category.BIOSTIMULANT)
                .imageUrl("http://example.com/image.jpg")
                .technicalSheetUrl("http://example.com/sheet.pdf")
                .build();

        testProductDTO = ProductDTO.builder()
                .id(1L)
                .name("Test Product")
                .description("Test Description")
                .category(Category.BIOSTIMULANT)
                .imageUrl("http://example.com/image.jpg")
                .technicalSheetUrl("http://example.com/sheet.pdf")
                .build();
        productService = new ProductServiceImpl(productRepository, mapper);
    }


    @Test
    void testCreateNewProductSuccess() {
        //Given
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        // When
        ProductDTO result = productService.createNewProduct(testProductDTO);

        // Then
        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        assertEquals(Category.BIOSTIMULANT, result.getCategory());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testCreateNewProductNullEntity() {   
        ProductDTO result = productService.createNewProduct(null);
        assertNull(result);
        verify(productRepository, never()).save(any());
    }

    @Test
    void testFindById() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(testProduct));

        Optional<ProductDTO> result = productService.findById(1L);
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("Test Product", result.get().getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<ProductDTO> result = productService.findById(999L);

        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    void testFindByPageSuccess() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Product> productPage = new PageImpl<>(Arrays.asList(testProduct));

        when(productRepository.findAll(pageable)).thenReturn(productPage);

        Page<ProductDTO> result = productService.findByPage(pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void testFindALLSuccess() {
          List<Product> products = Arrays.asList(testProduct);
        when(productRepository.findAll()).thenReturn(products);
      

        List<ProductDTO> result = productService.findALL();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Product", result.get(0).getName());
        verify(productRepository, times(1)).findAll();


    }

    @Test
    void testFindByCategorySuccess() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<Product> productPage = new PageImpl<>(Arrays.asList(testProduct));

        when(productRepository.findByCategory(Category.BIOSTIMULANT, pageable))
                .thenReturn(productPage);

        Page<ProductDTO> result = productService.findByCategory(Category.BIOSTIMULANT, pageable);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(productRepository, times(1)).findByCategory(Category.BIOSTIMULANT, pageable);
    }

    @Test
    void testUpdateProductSuccess() {
        Product updateP = Product.builder()
                .name("Updated Product")
                .description("Updated Description")
                .category(Category.BIOSTIMULANT)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any())).thenReturn(updateP);
    

        ProductDTO result = productService.updateProduct(1L, testProductDTO);

        assertNotNull(result);
        assertEquals("Updated Product",result.getName());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(testProduct);
        
    }

    @Test
    void testUpdateProductNotFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            productService.updateProduct(999L, testProductDTO);
        });
        verify(productRepository, times(1)).findById(999L);
    }

    @Test
    void testDeleteSuccess() {
        productService.delete(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }
}
