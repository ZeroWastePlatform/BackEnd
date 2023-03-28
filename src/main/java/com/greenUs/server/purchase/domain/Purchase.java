package com.greenUs.server.purchase.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.delivery.domain.Delivery;
import com.greenUs.server.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@ToString
@Getter
@Entity
public class Purchase extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "purchase_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne()
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private Integer totalPrice;

    public Purchase(Member member, Delivery delivery, int totalPrice) {
        this.member = member;
        this.delivery = delivery;
        this.totalPrice = totalPrice;
    }
}
