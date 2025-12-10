package com.atlashorticole.product_service.Mapper;

import org.springframework.stereotype.Component;
import com.atlashorticole.product_service.domain.Product;
import com.atlashorticole.product_service.dto.ProductDTO;

@Component
public class ProductMapper {

    private final FileMapper fileMapper;
    
    public ProductMapper(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }
    
    public ProductDTO toDto(Product p) {
        if (p == null) return null;
        return ProductDTO.builder()
                .id(p.getId())
                .name(p.getName())
                .category(p.getCategory())
                .description(p.getDescription())
                .resume(p.getResume())
                .dosage(p.getDosage())
                .composition(p.getComposition())
                .files(p.getFiles() != null ? 
                    p.getFiles().stream()
                        .map(fileMapper::toDto)
                        .toList() 
                    : null)
                .displayOrder(p.getDisplayOrder())
                .active(p.getActive())
                .build();
    }

    public Product toEntity(ProductDTO dto) {
        if (dto == null) return null;
        
        Product product = Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .category(dto.getCategory())
                .description(dto.getDescription())
                .resume(dto.getResume())
                .composition(dto.getComposition())
                .dosage(dto.getDosage())
                .displayOrder(dto.getDisplayOrder())
                .active(dto.getActive() != null ? dto.getActive() : true)
                .build();
        

        if (dto.getFiles() != null && !dto.getFiles().isEmpty()) {
            product.setFiles(dto.getFiles().stream()
                    .map(fileMapper::toEntity)
                    .peek(file -> file.setProduct(product))
                    .toList());
        }
        
        return product;
    }

    public void updateEntityFromDto(ProductDTO dto, Product entity) {
        if (dto == null || entity == null) return;
        
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getCategory() != null) entity.setCategory(dto.getCategory());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
        if (dto.getResume() != null) entity.setResume(dto.getResume());
        if (dto.getComposition() != null) entity.setComposition(dto.getComposition());
        if (dto.getDosage() != null) entity.setDosage(dto.getDosage());
        if (dto.getDisplayOrder() != null) entity.setDisplayOrder(dto.getDisplayOrder());
        if (dto.getActive() != null) entity.setActive(dto.getActive());
        
        // handler files
        if (dto.getFiles() != null) {
            entity.getFiles().clear(); // remove old files
            
            if (!dto.getFiles().isEmpty()) {
                entity.setFiles(dto.getFiles().stream()
                        .map(fileMapper::toEntity)
                        .peek(file -> file.setProduct(entity)) 
                        .toList());
            }
        }
    }
}