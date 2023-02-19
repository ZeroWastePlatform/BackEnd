package com.greenUs.server.member.dto.response;

import com.greenUs.server.purchase.dto.response.PurchaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class MyPagePurchaseResponse {

    private String name;
    private String nickname;
    private int level;
    private int zzimCnt; // 상품쪽 찜 구현 완료되면 진행
    private int point;
    private int couponCnt;

    private Page<PurchaseResponse> purchaseResponses;
}
