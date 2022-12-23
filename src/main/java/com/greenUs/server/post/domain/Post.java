package com.greenUs.server.post.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.member.domain.Member;

import javax.persistence.*;

@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
