package com.greenUs.server.product.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.purchase.domain.PurchaseProduct;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Product extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @OneToOne(mappedBy = "product")
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<PurchaseProduct> purchaseProducts =new ArrayList<>();



    private String title;
    private String description;
    private String brand;
    private String image;
    private ProductStatus productStatus;
    private int viewCount;
    private int price;

}

