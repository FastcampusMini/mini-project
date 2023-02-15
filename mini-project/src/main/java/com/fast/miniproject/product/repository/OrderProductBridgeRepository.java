package com.fast.miniproject.product.repository;

import com.fast.miniproject.product.entity.Order;
import com.fast.miniproject.product.entity.OrderProductBridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductBridgeRepository extends JpaRepository<OrderProductBridge,Long> {

    @Query(nativeQuery = true,value = "SELECT * FROM order_product as p WHERE p.orders IN (:order_list)")
    List<OrderProductBridge> findAllByOrderList(@Param("order_list") List<Order> order_list);

    List<OrderProductBridge> findAllByOrder(Order order);
}
