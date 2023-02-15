package com.fast.miniproject.product.service.impl;

import com.fast.miniproject.auth.dto.LoginReqDTO;
import com.fast.miniproject.auth.entity.User;
import com.fast.miniproject.auth.repository.UserRepository;
import com.fast.miniproject.global.response.ErrorResponseDTO;
import com.fast.miniproject.global.response.ResponseDTO;
import com.fast.miniproject.product.dto.OrderDetail;
import com.fast.miniproject.product.dto.ProductDTO;
import com.fast.miniproject.product.dto.ProductDetailDTO;
import com.fast.miniproject.product.entity.Order;
import com.fast.miniproject.product.entity.OrderProductBridge;
import com.fast.miniproject.product.entity.Product;
import com.fast.miniproject.product.entity.PurchasedProduct;
import com.fast.miniproject.product.repository.OrderProductBridgeRepository;
import com.fast.miniproject.product.repository.OrderRepository;
import com.fast.miniproject.product.repository.ProductRepository;
import com.fast.miniproject.product.repository.PurchaseProductRepository;
import com.fast.miniproject.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    private final UserRepository userRepository;
    private final PurchaseProductRepository purchaseProductRepository;
    private final OrderRepository orderRepository;
    private final OrderProductBridgeRepository orderProductBridgeRepository;

    @Override
    public ResponseDTO selectProductDetail(Long product_id) {
        try {
            Product product = productRepository.getReferenceById(product_id);
            ProductDetailDTO productDetailDTO = new ProductDetailDTO(product);
            return new ResponseDTO<>(productDetailDTO);

        }catch (Exception e){
            return new ErrorResponseDTO(500,"해당 상품의 상세정보를 찾지 못했습니다.").toResponse();
        }
    }

    @Override
    public ResponseDTO<?> selectProduct() {

        List<Product> product = productRepository.findAll();


        List<ProductDTO> productList = product.stream()
                .map(pro -> new ProductDTO(pro.getPrice(),pro.getBrand(),pro.getLogo(),pro.getName(),pro.getRate(),pro.getDetail()))
                .collect(Collectors.toList());

        return new ResponseDTO<>(productList);

    }

    @Override
    public ResponseDTO<?> recommendProduct(String email) {

        User user  = userRepository.findByEmail(email).get();

        int limitAmount =(int) (user.getSalary() * 2);

        List<Product> product = productRepository.findByPriceLessThanEqual(limitAmount);

        List<ProductDTO> productList = product.stream()
                .map(pro -> new ProductDTO(pro.getPrice(),pro.getBrand(),pro.getLogo(),pro.getName(),pro.getRate(),pro.getDetail()))
                .collect(Collectors.toList());




        return new ResponseDTO<>(productList);
    }

    @Override
    public ResponseDTO<?> buyProduct(ArrayList<Integer> products_id_list, LoginReqDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail()).get();
        List<Product> productList = productRepository.findAllByProductId(products_id_list);
        if (user==null || productList.size()==0)return new ErrorResponseDTO(500,"구매에 실패하였습니다.").toResponse();
        if(!isAvailableToPurchase(user,productList))return new ErrorResponseDTO(500,"대출 가능 금액을 초과하였습니다.").toResponse();

        try {
            List<PurchasedProduct> purchasedProducts = purchaseProductRepository.saveAll(toSaveList(productList));
            Order order = orderRepository.save(new Order(user));
            List<OrderProductBridge> orderList = new ArrayList<>();
            for (PurchasedProduct product: purchasedProducts){
                orderList.add(orderProductBridgeRepository.save(new OrderProductBridge(product,order)));
            }

            return new ResponseDTO(new OrderDetail(orderList));
        }catch (Exception e){
            return new ErrorResponseDTO(500,"구매에 실패하였습니다.").toResponse();
        }
    }

    @Override
    public ResponseDTO<?> orderCheck(LoginReqDTO dto) {
        try {
            User user = userRepository.findByEmail(dto.getEmail()).get();
            List<Order> orderList = orderRepository.findAllByUser(user);
            List<OrderDetail> resultList = new ArrayList<>();
            for (Order order:orderList){
                OrderDetail orderDetail = new OrderDetail(orderProductBridgeRepository.findAllByOrder(order));
                resultList.add(orderDetail);
            }
            return new ResponseDTO<>(resultList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseDTO<>("failed");
    }

    private boolean isAvailableToPurchase(User user,List<Product> productList){
        List<Order> orderList = orderRepository.findAllByUser(user);
        List<OrderProductBridge> list =orderProductBridgeRepository.findAllByOrderList(orderList);
        long spent =0;
        for (OrderProductBridge op : list){
            spent+=op.getPurchasedProduct().getPurchasedProductPrice();
        }
        long max =(user.getSalary()*2)-spent;
        long sum =0;
        for (Product p :productList){
            sum+=p.getPrice();
        }
        if (max<sum) {
            return false;
        }else {
            return true;
        }
    }


    private List<PurchasedProduct> toSaveList(List<Product> productList){
        List<PurchasedProduct> list = new ArrayList<>();
        for (Product product : productList){
            list.add(new PurchasedProduct(product));
        }
        return list;
    }



}
