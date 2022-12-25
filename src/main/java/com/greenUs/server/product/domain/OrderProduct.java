package com.greenUs.server.product.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.order.domain.Purchase;

import javax.persistence.*;

@Entity
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
