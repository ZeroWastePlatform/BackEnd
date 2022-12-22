package com.greenUs.server.order.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.delivery.domain.Delivery;
import com.greenUs.server.member.domain.Member;

import javax.persistence.*;

@Entity
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "order")
    private Delivery delivery;
}
