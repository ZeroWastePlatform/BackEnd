package com.greenUs.server.product.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;
    private String name;

    @OneToOne
    @JoinColumn(name ="product_id")
    private Product product;
}
