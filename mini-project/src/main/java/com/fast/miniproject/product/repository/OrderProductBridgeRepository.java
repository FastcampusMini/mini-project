package com.fast.miniproject.product.repository;

import com.fast.miniproject.product.entity.Orders;
import com.fast.miniproject.product.entity.OrderProductBridge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductBridgeRepository extends JpaRepository<OrderProductBridge,Long> {

    @Query(nativeQuery = true,value = "SELECT * FROM order_product as p WHERE p.orders IN (:orders_list)")
    List<OrderProductBridge> findAllByOrdersList(@Param("orders_list") List<Orders> orders_list);
}
