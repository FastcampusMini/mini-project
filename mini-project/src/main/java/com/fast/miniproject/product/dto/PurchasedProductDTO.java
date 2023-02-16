package com.fast.miniproject.product.dto;


import com.fast.miniproject.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchasedProductDTO {

    private Long puchasedId;
    private  String purchasedProductPrice;
    private String purchasedProductLogo;
    private String purchasedProductName;
    private double purchasedProductRate;
    private String purchasedProductDetail;
    private Product product;
}
