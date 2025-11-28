package com.atlashorticole.product_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.dto.ProductDTO;

public interface ProductService {

    ProductDTO createNewProduct(ProductDTO dto);

    Optional<ProductDTO> findById(Long id);
    Page<ProductDTO> findByPage(Pageable page);
    List<ProductDTO>  findALL();
    Page<ProductDTO> findByCategory(Category category,Pageable page);
    ProductDTO updateProduct(Long id , ProductDTO dto);
    void delete(Long id);
    

    
}
