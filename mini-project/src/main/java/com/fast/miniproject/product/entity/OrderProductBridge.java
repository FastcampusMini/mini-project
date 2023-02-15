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

    @ManyToOne(targetEntity=Order.class, fetch=FetchType.EAGER)
    @JoinColumn(name="orders")
    private Order order;

    @Builder
    public OrderProductBridge(PurchasedProduct purchasedProduct, Order order) {
        this.purchasedProduct = purchasedProduct;
        this.order = order;
    }
}
