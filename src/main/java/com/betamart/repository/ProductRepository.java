package com.betamart.repository;

import com.betamart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsDeleted(boolean isDeleted);
    boolean existsByName(String name);
}
