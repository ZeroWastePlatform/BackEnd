package com.greenUs.server.basket.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.product.domain.Product;

import javax.persistence.*;

@Entity
public class Basket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
