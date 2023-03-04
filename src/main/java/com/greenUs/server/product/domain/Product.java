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

    private String category;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<PurchaseProduct> purchaseProducts =new ArrayList<>();


    private String refundInfo;
    private String deliveryInfo;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();
    private String discountRate;
    private String badges;
    private String title;
    private String description;
    private String brand;
    private String thumbnail;
    private String info;
    private ProductStatus productStatus;
    private int viewCount;
    private int price;
    private int likeCount;
    private int reviewCount =0;
    private int askCount =0;

    public void plusLikeCount() {
        this.likeCount = this.likeCount+1;
    }
    public void minusLikeCount() {
        this.likeCount = this.likeCount-1;
    }
}

