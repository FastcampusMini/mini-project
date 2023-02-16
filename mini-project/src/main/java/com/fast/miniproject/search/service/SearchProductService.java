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

    public Page<ProductDTO> searchProductsByName(String name, Pageable pageable) {
        return searchProductRepository.findAllByNameContains(name, pageable)
                .map(ProductDTO::new);
    }

}