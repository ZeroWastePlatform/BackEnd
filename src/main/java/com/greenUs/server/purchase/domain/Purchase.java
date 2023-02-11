package com.greenUs.server.purchase.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.delivery.domain.Delivery;
import com.greenUs.server.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "purchase",cascade = CascadeType.ALL)
    private List<PurchaseProduct> purchaseProducts = new ArrayList<>();

    private int totalPrice;

    // 임시로 해둠
    public Purchase(Member member, Delivery delivery) {
        this.member = member;
        this.delivery = delivery;
    }
}
