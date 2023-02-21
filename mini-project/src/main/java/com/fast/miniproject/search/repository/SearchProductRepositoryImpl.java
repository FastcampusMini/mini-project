package com.fast.miniproject.search.repository;

import com.fast.miniproject.product.entity.Product;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.fast.miniproject.product.entity.QProduct.product;

public class SearchProductRepositoryImpl extends QuerydslRepositorySupport implements SearchProductRepositoryCustom {

    public SearchProductRepositoryImpl() { super(Product.class); }

    @Override
    public Page<Product> searchQuery(String searchTarget, String searchKeyword, String sortTarget, String sortDirection, Pageable pageable) {
        QueryResults<Product> results =
                from(product)
                        .where(productSearch(searchTarget, searchKeyword))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .orderBy(productSort(sortTarget, sortDirection))
                        .fetchResults();

        List<Product> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private OrderSpecifier<?> productSort(String sortTarget, String sortDirection) {
        Order direction = sortDirection.equals("ASC") ? Order.ASC : Order.DESC;

        switch (sortTarget) {
            case "name":
                return new OrderSpecifier<>(direction, product.name);
            case "brand":
                return new OrderSpecifier<>(direction, product.brand);
            case "price":
                return new OrderSpecifier<>(direction, product.price);
        }
        return null;
    }

    private BooleanBuilder productSearch(String searchTarget, String searchKeyword) {
        switch (searchTarget) {
            case "name":
                return new BooleanBuilder().and(product.name.contains(searchKeyword));
            case "brand":
                return new BooleanBuilder().and(product.brand.contains(searchKeyword));
            case "price":
                int price;
                if (searchKeyword.isEmpty()) {
                    price = 1000000000;
                    //price로 검색할때 검색어가 없을때의 처리로 그냥 엄청 큰 값을 넣어 모든 product가 나오게 한다
                } else {
                    price = Integer.parseInt(searchKeyword);
                }
                return new BooleanBuilder().and(product.price.between(0, price));
        }
        return null;
    }
}
