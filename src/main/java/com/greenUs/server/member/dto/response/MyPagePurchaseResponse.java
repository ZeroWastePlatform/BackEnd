package com.greenUs.server.member.dto.response;

import com.greenUs.server.purchase.dto.response.PurchaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class MyPagePurchaseResponse {
    private MyPageProfileResponse myPageProfileResponse;
    private Page<PurchaseResponse> purchaseResponses;
}
