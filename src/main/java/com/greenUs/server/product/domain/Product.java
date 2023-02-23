package com.greenUs.server.product.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.purchase.domain.PurchaseProduct;
import com.greenUs.server.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<PurchaseProduct> purchaseProducts =new ArrayList<>();


    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    private String title;
    private String description;
    private String brand;
    private String image;
    private ProductStatus productStatus;
    private int viewCount;
    private int price;

}

