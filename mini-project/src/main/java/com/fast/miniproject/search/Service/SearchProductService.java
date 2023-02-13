package com.fast.miniproject.search.Service;

import com.fast.miniproject.product.dto.ProductDTO;
import com.fast.miniproject.search.repository.SearchProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SearchProductService {

    private final SearchProductRepository searchProductRepository;

    public List<ProductDTO> searchProductsByName(String name) {
        return searchProductRepository.findAllByNameContains(name)
                .stream()
                .map(ProductDTO::new)
                .collect(toList());
    }

}
