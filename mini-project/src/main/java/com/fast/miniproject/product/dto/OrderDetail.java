package com.fast.miniproject.product.dto;

import com.fast.miniproject.product.entity.Order;
import com.fast.miniproject.product.entity.OrderProductBridge;
import com.fast.miniproject.product.entity.PurchasedProduct;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderDetail {
    private Order order;
    private List<PurchasedProduct> productList;

    public OrderDetail(List<OrderProductBridge> list){
        this.order=list.get(0).getOrder();
        order.setUser(null);
        List<PurchasedProduct> products= new ArrayList<>();
        for (OrderProductBridge orderProductBridge: list){
            products.add(orderProductBridge.getPurchasedProduct());
        }
        this.productList=products;
    }
}
