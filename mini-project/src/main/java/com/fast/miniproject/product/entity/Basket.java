package com.fast.miniproject.product.entity;

import com.fast.miniproject.auth.entity.User;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="basket")
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_id")
    private Long basketId;

    @ManyToOne(targetEntity=Product.class, fetch=FetchType.LAZY)
    @JoinColumn(name="product")
    private Product product;

    @ManyToOne(targetEntity= User.class, fetch=FetchType.LAZY)
    @JoinColumn(name="member")
    private User user;
}
