package com.greenUs.server.purchase.service;

import com.greenUs.server.delivery.domain.Delivery;
import com.greenUs.server.delivery.repository.DeliveryRepository;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.purchase.domain.Purchase;
import com.greenUs.server.purchase.domain.PurchaseProduct;
import com.greenUs.server.purchase.dto.ShippingAddress;
import com.greenUs.server.purchase.dto.request.PurchaseRequest;
import com.greenUs.server.purchase.dto.response.PurchaseResponse;
import com.greenUs.server.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final MemberRepository memberRepository;
    private final DeliveryRepository deliveryRepository;
    @Transactional
    public PurchaseResponse createPurchase(PurchaseRequest purchaseRequest) {
        String email = purchaseRequest.getEmail();
        Member member = memberRepository.findByEmail(email);

        ShippingAddress address = purchaseRequest.getAddress();
        // delivery 부터 저장
        Delivery delivery = Delivery.builder()
                        .addressName(address.getAddressName())
                        .recipient(address.getRecipient())
                        .recipientPhone(address.getRecipientPhone())
                        .zipCode(address.getZipCode())
                        .address(address.getAddress())
                        .addressDetail(address.getAddressDetail())
                        .deliverDate("2023-02-20") // 임의로 해둔 배송 출발일
                        .build();

        deliveryRepository.save(delivery);

        Purchase purchase = purchaseRepository.save(new Purchase(member, delivery));
        return PurchaseResponse.builder()
                .id(purchase.getId())
                .delivery(purchase.getDelivery())
                .purchaseProducts(purchase.getPurchaseProducts())
                .totalPrice(purchase.getTotalPrice())
                .build();
    }

    public Page<PurchaseResponse> getList() {

        return null;
    }

    public PurchaseResponse getById(Long id) {

        return null;
    }

    public void deletePurchase(Long id) {

    }

}
