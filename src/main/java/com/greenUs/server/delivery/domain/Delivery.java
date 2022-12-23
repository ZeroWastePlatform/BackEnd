package com.greenUs.server.delivery.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.purchase.domain.Purchase;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    private String deliverDate;
}
