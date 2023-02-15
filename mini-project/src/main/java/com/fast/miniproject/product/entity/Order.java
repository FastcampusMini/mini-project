package com.fast.miniproject.product.entity;

import com.fast.miniproject.auth.entity.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name="orders")
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(targetEntity= User.class, fetch=FetchType.EAGER)
    @JoinColumn(name="member")
    private User user;

    @CreationTimestamp
    @Column(name = "order_date")
    private LocalDateTime order_date;

    @Builder
    public Order(User user) {
        this.user = user;
    }
}
