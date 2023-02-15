package com.fast.miniproject.search.controller;

import com.fast.miniproject.global.response.ResponseDTO;
import com.fast.miniproject.product.dto.ProductDTO;
import com.fast.miniproject.search.service.SearchProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchProductController {

    private final SearchProductService searchProductService;

    @GetMapping
    public ResponseDTO<List<ProductDTO>> getProductByName(@RequestParam String name) {
        return new ResponseDTO<List<ProductDTO>>(searchProductService.searchProductsByName(name));
    }

}
