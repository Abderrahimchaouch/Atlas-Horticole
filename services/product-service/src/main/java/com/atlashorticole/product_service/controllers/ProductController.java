package com.atlashorticole.product_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atlashorticole.product_service.domain.Product;
import com.atlashorticole.product_service.dto.ProductDTO;
import com.atlashorticole.product_service.services.ProductService;
import com.atlashorticole.product_service.utils.Utils;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("api/products")
@Validated
public class ProductController {

    private  final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProductDTO>> getByPageProduct( @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size ,
                                        @RequestParam(defaultValue = "id,asc") String sort ) {
        Sort sortOrder = Utils.checkParamSort(sort);
        Page<ProductDTO> result = productService.findByPage(PageRequest.of(page, size, sortOrder));

        return ResponseEntity.ok(result);
    }
    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getAllProduct() {
       
        List<ProductDTO> result = productService.findALL();

        return ResponseEntity.ok(result);
    }

    @PostMapping("create")
    public ResponseEntity<ProductDTO> create(@Valid @RequestBody ProductDTO dto) {
        ProductDTO created = productService.createNewProduct(dto);
        return ResponseEntity.created(URI.create("/api/products/" + created.getId())).body(created);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
       
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updtae(@PathVariable Long id,@Valid  @RequestBody ProductDTO dto) {
         
        ProductDTO updateDto = productService.updateProduct(id, dto);
        
        return ResponseEntity.ok(updateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id ){
        productService.delete(id);
        return ResponseEntity.noContent().build();

    }
    

    
    
}
