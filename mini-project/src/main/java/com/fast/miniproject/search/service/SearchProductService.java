package com.fast.miniproject.search.service;

import com.fast.miniproject.product.dto.ProductDTO;
import com.fast.miniproject.search.repository.SearchProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchProductService {

    private final SearchProductRepository searchProductRepository;

    public Page<ProductDTO> searchProducts(String searchTarget, String searchKeyword, String sortTarget, String sortKeyword, Pageable pageable) {
        return searchProductRepository.searchByBuilder(searchTarget, searchKeyword, sortTarget, sortKeyword, pageable)
                .map(ProductDTO::new);
    }

}