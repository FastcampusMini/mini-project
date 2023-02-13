package com.fast.miniproject.product.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="product")
public class Product {

    @Id
    @Column(name="product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name="product_brand")
    private String brand;

    @Column(name="product_logo")
    private String logo;

    @Column(name="product_name")
    private String name;

    @Column(name="product_rate")
    private int rate;

    @Column(name="detail")
    private String detail;
}
