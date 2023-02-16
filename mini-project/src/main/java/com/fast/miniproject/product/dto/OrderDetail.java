package com.fast.miniproject.product.dto;

import com.fast.miniproject.product.entity.Orders;
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
    private Orders orders;
    private List<PurchasedProduct> productList;

    public OrderDetail(List<OrderProductBridge> list){
        this.orders =list.get(0).getOrders();
        orders.setUser(null);
        List<PurchasedProduct> products= new ArrayList<>();
        for (OrderProductBridge orderProductBridge: list){
            products.add(orderProductBridge.getPurchasedProduct());
        }
        this.productList=products;
    }
}
