package com.atlashorticole.product_service.dto;
import java.util.ArrayList;
import java.util.List;

import com.atlashorticole.product_service.domain.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDTO{

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Category category;

    private String description;

    private String resume;

    private String dosage;

    private String composition;


    private List<FileDTO> files;
    private Integer displayOrder;

    @Builder.Default
    private Boolean active = true;

}
