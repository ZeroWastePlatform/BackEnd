package com.greenUs.server.purchase.service;

import com.greenUs.server.delivery.domain.Delivery;
import com.greenUs.server.delivery.repository.DeliveryRepository;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.exception.NotFoundProductException;
import com.greenUs.server.product.repository.ProductRepository;
import com.greenUs.server.purchase.domain.Purchase;
import com.greenUs.server.purchase.domain.PurchaseProduct;
import com.greenUs.server.purchase.dto.ShippingAddress;
import com.greenUs.server.purchase.dto.request.PurchaseRequest;
import com.greenUs.server.purchase.dto.response.PurchaseResponse;
import com.greenUs.server.purchase.repository.PurchaseProductRepository;
import com.greenUs.server.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final PurchaseProductRepository purchaseProductRepository;
    private final MemberRepository memberRepository;
    private final DeliveryRepository deliveryRepository;

    @Transactional
    public PurchaseResponse createPurchase(PurchaseRequest purchaseRequest, Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
        ShippingAddress address = purchaseRequest.getAddress();
        LocalDateTime now = LocalDateTime.now();
        String formattedNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // delivery
        Delivery delivery = new Delivery(
                address.getAddressName(),
                address.getRecipient(),
                address.getRecipientPhone(),
                address.getZipCode(),
                address.getAddress(),
                address.getAddressDetail(),
                formattedNow
        );
        deliveryRepository.save(delivery);
        Purchase purchase = purchaseRepository.save(new Purchase(member, delivery, purchaseRequest.getTotalPrice()));

        List<Long> productsId = purchaseRequest.getProductsId();
        for (Long productId: productsId) {
            Product product = productRepository.findById(productId).orElseThrow(NotFoundProductException::new);
            purchaseProductRepository.save(new PurchaseProduct(purchase, product));
        }

        return new PurchaseResponse(purchase);
    }

    public Page<PurchaseResponse> getList(Long id) {

        return null;
    }

}
