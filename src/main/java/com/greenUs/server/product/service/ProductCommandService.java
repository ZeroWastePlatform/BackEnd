package com.greenUs.server.product.service;

import com.greenUs.server.auth.dto.LoginMember;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.product.domain.ProductLike;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.dto.request.CreateProductDto;
import com.greenUs.server.product.dto.request.LikeDto;
import com.greenUs.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommandService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    public Long createProduct(CreateProductDto createProductDto) {
        Product product = Product.builder()
                .title(createProductDto.getTitle())
                .description(createProductDto.getDescription())
                .image(createProductDto.getImage())
                .brand(createProductDto.getBrand())
                .viewCount(0)
                .price(createProductDto.getPrice())
                .build();
        productRepository.save(product);
        return product.getId();
    }
    public Long likeProduct(LikeDto likeDto, LoginMember loginMember){
        Member member = memberRepository.findByIdFetchLikes(loginMember.getId()).orElseThrow(NotFoundMemberException::new);
        Product product = productRepository.findById(likeDto.getProductId()).orElseThrow(IllegalArgumentException::new);
        if(likeDto.isLike()){
            ProductLike productLike = ProductLike.builder()
                    .member(member)
                    .productId(likeDto.getProductId())
                    .build();
            member.getProductLikes().add(productLike);
            product.plusLikeCount();
            return likeDto.getProductId();
        }
        for(ProductLike productLike : member.getProductLikes()){
            if(productLike.getProductId() == likeDto.getProductId()){
                member.getProductLikes().remove(productLike);
                product.minusLikeCount();
            }
        }
        return likeDto.getProductId();
    }
}
