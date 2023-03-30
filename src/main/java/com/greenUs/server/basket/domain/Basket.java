package com.greenUs.server.basket.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.product.domain.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Basket extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "basket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    protected Basket(Member member, Product product) {
        this.member=member;
        this.product=product;
    }
}
