package com.fast.miniproject.search.repository;

import com.fast.miniproject.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByNameContains(String name, Pageable pageable);
}