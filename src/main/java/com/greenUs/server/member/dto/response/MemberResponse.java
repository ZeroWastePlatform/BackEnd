package com.greenUs.server.member.dto.response;

import com.greenUs.server.member.domain.Address;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.domain.SocialType;
import lombok.Getter;

@Getter
public class MemberResponse {

    private Long id;
    private SocialType socialType;
    private String email;
    private String name;
    private String nickname;
    private Address address;
    private String phoneNum;
    private String interestArea;
    private int level;
    private int point;

    public MemberResponse(Member member) {
        this.id = member.getId();
        this.socialType = member.getSocialType();
        this.email = member.getEmail();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.address = member.getAddress();
        this.phoneNum = member.getPhoneNum();
        this.interestArea = member.getInterestArea();
        this.level = member.getLevel();
        this.point = member.getPoint();
    }
}
