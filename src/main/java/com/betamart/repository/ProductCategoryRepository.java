package com.betamart.repository;

import com.betamart.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByIsDeleted(boolean isDeleted);
    boolean existsByName(String name);
}
