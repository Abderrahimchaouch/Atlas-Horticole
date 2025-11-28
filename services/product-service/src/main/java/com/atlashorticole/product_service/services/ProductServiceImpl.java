package com.atlashorticole.product_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.atlashorticole.product_service.Mapper.ProductMapper;
import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.domain.Product;
import com.atlashorticole.product_service.dto.ProductDTO;
import com.atlashorticole.product_service.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ProductMapper mapper ;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository ,ProductMapper mapper){
        this.productRepository = productRepository;
        this.mapper = mapper;

    }

    @Override
    public ProductDTO createNewProduct(ProductDTO dto) {
        Product product = mapper.toEntity(dto);
        if(product!=null){
        Product saveProduct = this.productRepository.save(product);
       return mapper.toDto(saveProduct);
        }
        return null;
    }

    @Override
    public Optional<ProductDTO> findById(Long id) {

        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            ProductDTO dto = mapper.toDto(product.get());
            return Optional.of(dto);
        }
        else return Optional.empty();
        // mapper::toDto equivaut de product->mapper.toDto(product
        //return productRepository.findById(id).map(product->mapper.toDto(product));
    }

    @Override
    public Page<ProductDTO> findByPage(Pageable page) {
        return productRepository.findAll(page).map(mapper::toDto);

    }

   @Override
    public List<ProductDTO> findALL() {
        return productRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }


    @Override
    public Page<ProductDTO> findByCategory(Category category ,Pageable page) {
            return productRepository.findByCategory(category, page).map(mapper::toDto);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product existingProduct = productRepository.findById(id).
                                    orElseThrow(()->new IllegalArgumentException("Product not found"+id));
        mapper.updateEntityFromDto(dto, existingProduct);
        
        Product saveProduct = productRepository.save(existingProduct);
        return mapper.toDto(saveProduct);
     
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
    
    
}
