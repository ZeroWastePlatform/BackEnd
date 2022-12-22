package com.greenUs.server.delivery.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.order.domain.Order;

import javax.persistence.*;

@Entity
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
