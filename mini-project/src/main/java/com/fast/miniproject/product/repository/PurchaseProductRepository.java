package com.fast.miniproject.product.repository;

import com.fast.miniproject.product.entity.PurchasedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseProductRepository extends JpaRepository<PurchasedProduct,Long> {

    void deleteByPurchasedProductId(Long pruchasedProductId);

}
