package com.fast.miniproject.product.controller;

import com.fast.miniproject.auth.dto.LoginReqDTO;
import com.fast.miniproject.product.dto.ProductIdList;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fast.miniproject.global.response.ResponseDTO;
import com.fast.miniproject.product.dto.ProductDTO;
import com.fast.miniproject.product.entity.Product;
import com.fast.miniproject.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/product/detail")
    public ResponseDTO<?> productDetail(@RequestParam Long products_id){
        return productService.selectProductDetail(products_id);
    }

    @GetMapping("/products")
    public ResponseDTO<?> selectProduct( ){
        return productService.selectProduct();
    }

    @GetMapping("/product/recommend")
    public ResponseDTO<?> recommendProduct(@AuthenticationPrincipal LoginReqDTO loginReqDTO){
        return productService.recommendProduct(loginReqDTO.getEmail());
    }

    @PostMapping("/products/buy")
    public ResponseDTO<?> buyProducts(@AuthenticationPrincipal LoginReqDTO user, ProductIdList products_id_list){
        return productService.buyProduct(products_id_list.getProducts_id_list(),user);
    }

    @GetMapping("/product/order/check")
    public ResponseDTO<?> orderCheck(@AuthenticationPrincipal LoginReqDTO dto){
         return productService.orderCheck(dto);
    }
}
