package com.fast.miniproject.product.service;

import com.fast.miniproject.auth.dto.LoginReqDTO;
import com.fast.miniproject.global.response.ResponseDTO;
import com.fast.miniproject.product.dto.ProductDTO;

import java.util.ArrayList;

public interface ProductService {

    ResponseDTO<?> selectProductDetail(Long product_id);
    
    public ResponseDTO<?> selectProduct();


    ResponseDTO<?> recommendProduct(String email);
    ResponseDTO<?> buyProduct(ArrayList<Integer> products_id_list, LoginReqDTO user);

    ResponseDTO<?> orderCheck(LoginReqDTO dto);
}
