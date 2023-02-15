package com.fast.miniproject.product.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "purchased_product")
@ToString
public class PurchasedProduct {

    @Id
    @Column(name = "purchased_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchasedProductId;

    @Column(name = "purchased_product_price")
    private int purchasedProductPrice;

    @Column(name = "purchased_product_brand")
    private String purchasedProductBrand;

    @Column(name = "purchased_product_logo")
    private String purchasedProductLogo;

    @Column(name = "purchased_product_name")
    private String purchasedProductName;

    @Column(name = "purchased_product_rate")
    private double purchasedProductRate;

    @Column(name="purchased_product_detail")
    private String purchasedProductDetail;

    @ManyToOne(targetEntity=Product.class, fetch=FetchType.EAGER)
    @JoinColumn(name="product")
    private Product product;


    public PurchasedProduct(Product product) {
        this.purchasedProductPrice = product.getPrice();
        this.purchasedProductBrand = product.getBrand();
        this.purchasedProductLogo = product.getLogo();
        this.purchasedProductName = product.getName();
        this.purchasedProductRate = product.getRate();
        this.purchasedProductDetail = product.getDetail();
        this.product = product;
    }
}
