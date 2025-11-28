package com.atlashorticole.product_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.atlashorticole.product_service.domain.Category;
import com.atlashorticole.product_service.domain.Product;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public Optional<Product> findByName(String name);

    public Page<Product> findByCategory(Category category, Pageable page);

    public Page<Product> findAllByActiveTrue(Pageable page);

    
}
