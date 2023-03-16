package com.greenUs.server.product.domain;

import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.product.converter.BadgeConverter;
import com.greenUs.server.product.converter.CategoryConverter;
import com.greenUs.server.product.converter.ProductStatusConverter;
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

    private String refundInfo; // 환불 정보 (환불 배송지, 환불 가격 ... )

    private String deliveryInfo; // 배송지 정보 (배송지, 배송 가격 ... )

    @Convert(converter = CategoryConverter.class)
    private Category category;

    private String discountRate;

    @Convert(converter = BadgeConverter.class)
    private Badge badges;

    private String title;

    private String description;

    private String brand;

    @Convert(converter = ProductStatusConverter.class)
    private ProductStatus productStatus;

    private Integer viewCount;

    private Integer price;
    private Integer likeCount;

    private Integer reviewCount;

    private Integer askCount;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<PurchaseProduct> purchaseProducts =new ArrayList<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Attachment> images = new ArrayList<>();

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

