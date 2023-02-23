package com.fast.miniproject.search.service;

import com.fast.miniproject.auth.dto.LoginReqDTO;
import com.fast.miniproject.auth.entity.User;
import com.fast.miniproject.auth.exception.UserException;
import com.fast.miniproject.auth.exception.UserExceptionType;
import com.fast.miniproject.auth.repository.UserRepository;
import com.fast.miniproject.product.dto.ProductDTO;
import com.fast.miniproject.product.service.ProductService;
import com.fast.miniproject.search.repository.SearchProductRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchProductService {

    private final SearchProductRepository searchProductRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    public Page<ProductDTO> searchProducts(String searchTarget, String searchKeyword, String sortTarget, String sortKeyword, Pageable pageable) {
        return searchProductRepository.searchQuery(searchTarget, searchKeyword, sortTarget, sortKeyword, pageable, null)
                .map(ProductDTO::new);
    }

    public Page<ProductDTO> searchProducts(String searchTarget, String searchKeyword, String sortTarget, String sortKeyword, Pageable pageable, LoginReqDTO loginReqDTO) {
        User user = userRepository.findByEmail(loginReqDTO.getEmail()).orElseThrow(() -> new UserException(UserExceptionType.NON_EXISTENT_USER));
        BooleanBuilder booleanBuilder = new BooleanBuilder(searchProductRepository.searchQueryWithUser(productService.availableAmount(user)));
        return searchProductRepository.searchQuery(searchTarget, searchKeyword, sortTarget, sortKeyword, pageable, booleanBuilder)
                .map(ProductDTO::new);
    }

}