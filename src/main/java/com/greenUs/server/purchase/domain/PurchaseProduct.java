package com.greenUs.server.purchase.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.product.domain.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PurchaseProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public PurchaseProduct(Purchase purchase, Product product) {
        this.purchase = purchase;
        this.product = product;
    }
}
