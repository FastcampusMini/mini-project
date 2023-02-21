package com.fast.miniproject.search.repository;

import com.fast.miniproject.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProductRepositoryCustom {
    Page<Product> searchByBuilder(String searchTarget, String searchKeyword, String sortTarget, String sortKeyword, Pageable pageable);
}
