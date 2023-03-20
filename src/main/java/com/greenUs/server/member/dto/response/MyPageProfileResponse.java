package com.greenUs.server.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPageProfileResponse {

    private Long id;
    private String name;
    private String nickname;
    private int level;
    private int zzimCnt; // 상품쪽 찜 구현 완료되면 진행
    private int point;
    private int couponCnt;
}
