package com.greenUs.server.product.domain;

import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.product.converter.BadgeConverter;
import com.greenUs.server.product.converter.CategoryConverter;
import com.greenUs.server.purchase.domain.PurchaseProduct;
import com.greenUs.server.reviewAndAsk.domain.Ask;
import com.greenUs.server.reviewAndAsk.domain.Review;
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

    @Convert(converter = CategoryConverter.class)
    private Category category;

    private Integer discountRate;

    @Convert(converter = BadgeConverter.class)
    private Badge badges;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Brand brand;

    private Integer viewCount;

    private Integer stock;

    private Integer price;

    private Integer deliveryFee;
    private Integer likeCount;

    private Integer reviewCount;

    private Integer askCount;

    private String thumbnail;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<PurchaseProduct> purchaseProducts =new ArrayList<>();
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Ask> asks = new ArrayList<>();


    public void plusLikeCount() {
        this.likeCount = this.likeCount+1;
    }
    public void minusLikeCount() {
        this.likeCount = this.likeCount-1;
    }
}

