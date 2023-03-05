package com.greenUs.server.reviewAndAsk.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ask extends BaseEntity {

    @Id
    private Long id;

    private String content;
    private String title;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private String answer = "";
    private boolean secret;
    private String category;

}
