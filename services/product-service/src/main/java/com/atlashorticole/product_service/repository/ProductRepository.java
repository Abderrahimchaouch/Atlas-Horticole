package com.atlashorticole.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.domain.Product;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public Optional<Product> findByName(String name);

    public Page<Product> findByCategory(Category category, Pageable page);

    public Page<Product> findAllByActiveTrue(Pageable page);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.files WHERE p.id = :id")
    Optional<Product> findByIdWithFiles(@Param("id") Long id);
    
}
