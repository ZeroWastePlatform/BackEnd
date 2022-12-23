package com.greenUs.server.product.domain;

import com.greenUs.server.common.BaseEntity;

import javax.persistence.*;

@Entity
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
}
