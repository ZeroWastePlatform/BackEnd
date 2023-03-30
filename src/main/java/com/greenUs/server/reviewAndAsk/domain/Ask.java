package com.greenUs.server.reviewAndAsk.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.product.domain.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ask extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "ask_id")
    private Long id;

    private String content;

    private String title;

    private String answer;

    private Boolean secret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
