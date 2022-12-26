package com.greenUs.server.member.domain;

import com.greenUs.server.common.BaseEntity;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    private SocialType socialType;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    private String address; // TODO: embedded 로 수정하기

    @Column(name = "phone_num", nullable = false)
    private String phoneNum;

    @Column(name = "interest_area", nullable = false)
    private String interestArea;

}
