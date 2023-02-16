package com.fast.miniproject.product.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name="order_product")
@ToString
public class OrderProductBridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bridge_id")
    private Long bridgeId;

    @ManyToOne(targetEntity=PurchasedProduct.class, fetch=FetchType.EAGER)
    @JoinColumn(name="purchased_product")
    private PurchasedProduct purchasedProduct;

    @ManyToOne(targetEntity= Orders.class, fetch=FetchType.EAGER)
    @JoinColumn(name="orders")
    private Orders orders;

    @Builder
    public OrderProductBridge(PurchasedProduct purchasedProduct, Orders orders) {
        this.purchasedProduct = purchasedProduct;
        this.orders = orders;
    }
}
