package com.greenUs.server.product.repository;

import com.greenUs.server.product.domain.*;
import com.greenUs.server.product.dto.Price;
import com.greenUs.server.product.dto.ProductStatus;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
        public Page<Product> findWithSearchCondition(Category category, Brand brand, Price price, ProductStatus productStatus, Pageable pageable) {

        List<Product> productList = jpaQueryFactory.selectFrom(QProduct.product)
                .where(
                        eqCategory(category),
                        erBrand(brand),
                        containPrice(price),
                        eqProductStatus(productStatus)
                )
                .orderBy(getOrderSpecifier(pageable.getSort()).stream().toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = jpaQueryFactory.select(QProduct.product.count())
                .from(QProduct.product)
                .fetchOne();

        return new PageImpl<>(productList, pageable, count);

    }

    private BooleanExpression eqCategory(Category category) {
        if (category == null)
            return null;
        return QProduct.product.category.eq(category);
    }

    private BooleanExpression erBrand(Brand brand) {
        if (brand == null)
            return null;
        return QProduct.product.brand.eq(brand);
    }

    private BooleanExpression containPrice(Price price) {
        if (price == null)
            return null;
        switch (price) {
            case LT_10:
                return QProduct.product.price.lt(10000);
            case BT_10_30:
                return QProduct.product.price.between(10000, 30000);
            case BT_30_50:
                return QProduct.product.price.between(30000, 50000);
            case GT_50:
                return QProduct.product.price.gt(50000);
            default:
                return null;
        }
    }

    private BooleanExpression eqProductStatus(ProductStatus productStatus) {
        if (productStatus == null)
            return null;
        switch (productStatus) {
            case FREE_SHIPPING:
                return QProduct.product.deliveryFee.eq(0);
            case DISCOUNT_STOCK:
                return QProduct.product.discountRate.gt(0);
            case REMOVE_OUT_OF_STOCK:
                return QProduct.product.stock.gt(0);
            default:
                return null;
        }
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order dir = order.isDescending() ? Order.DESC : Order.ASC;
            String property = order.getProperty();
            PathBuilder pathBuilder = new PathBuilder(Product.class, "product");
            orders.add(new OrderSpecifier(dir, pathBuilder.get(property)));
        });
        return orders;
    }
}
