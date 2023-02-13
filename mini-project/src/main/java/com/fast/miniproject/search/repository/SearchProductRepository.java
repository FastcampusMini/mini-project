package com.fast.miniproject.search.repository;

import com.fast.miniproject.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByNameContains(String name);
}
