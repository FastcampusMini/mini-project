package com.fast.miniproject.product.dto;

import com.fast.miniproject.product.entity.OrderProductBridge;
import com.fast.miniproject.product.entity.Orders;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderListResp {
    List<OrderDetail> list;

    public OrderListResp(List<OrderProductBridge> orderProductBridges){
        ArrayList<OrderProductBridge> orderProductBridgeArrayList = new ArrayList<>();
        List<OrderDetail> respList = new ArrayList<>();
        Orders orders = null;
        for (int i=0;i<orderProductBridges.size();i++){
            if (orders ==null || orders == orderProductBridges.get(i).getOrders()){
                orderProductBridgeArrayList.add(orderProductBridges.get(i));
                orders =orderProductBridges.get(i).getOrders();
            }else {
                respList.add(new OrderDetail(orderProductBridgeArrayList));
                orderProductBridgeArrayList = new ArrayList<>();
                orderProductBridgeArrayList.add(orderProductBridges.get(i));
                orders =orderProductBridges.get(i).getOrders();
            }
        }
        respList.add(new OrderDetail(orderProductBridgeArrayList));
        this.list = respList;
    }
}
