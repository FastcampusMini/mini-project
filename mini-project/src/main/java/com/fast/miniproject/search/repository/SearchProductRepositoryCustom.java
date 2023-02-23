package com.fast.miniproject.search.repository;

import com.fast.miniproject.product.entity.Product;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchProductRepositoryCustom {
    Page<Product> searchQuery(String searchTarget, String searchKeyword, String sortTarget, String sortKeyword, Pageable pageable, BooleanBuilder booleanBuilder);

    BooleanBuilder searchQueryWithUser(Long availableAmount);
}
