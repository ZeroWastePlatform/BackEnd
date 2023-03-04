package com.greenUs.server.product.service;


import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.product.domain.Product;
import com.greenUs.server.product.dto.respond.GetInfoDto;
import com.greenUs.server.product.dto.respond.GetProductDetailDto;
import com.greenUs.server.product.dto.respond.GetProductDto;
import com.greenUs.server.product.dto.respond.GetRecommendDto;
import com.greenUs.server.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService {
    //Mapper 보류(논의) 그냥 builder()?
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    public Page<GetProductDto> getProducts(Pageable pageable){
        Page<Product> products = productRepository.findAll(pageable);
        Page<GetProductDto> productForms = products.map(product -> makeProductForm(product));
        return productForms;
    }

    public Page<GetProductDto> getProductByCategory(String categoryName, Pageable pageable){
        Page<Product> products = productRepository.findAllByCategory(categoryName,pageable);
        Page<GetProductDto> productForms = products.map(product -> makeProductForm(product));
        return productForms;
    }
    public GetInfoDto getInfoNavigation(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(IllegalAccessError::new);
        GetInfoDto getInfoDto = GetInfoDto.builder()
                .ask(product.getAskCount())
                .review(product.getReviewCount())
                .build();
        return getInfoDto;
    }

    public List<GetRecommendDto> getRecommends(Long productId,Long userId){
        Product product = productRepository.findById(productId).orElseThrow(IllegalAccessError::new);
        List<Product> findProducts = productRepository.findTop10ByCategory(product.getCategory().toString());
        List<GetRecommendDto> getRecommendDtos = new ArrayList<>();
        for(int i=0;i<findProducts.size();i++){
            Product findProduct = findProducts.get(i);
            if(findProducts.get(i).getId() == product.getId()){
                continue;
            }
            GetRecommendDto getRecommendDto = GetRecommendDto.builder()
                    .id(findProduct.getId())
                    .category(findProduct.getCategory().toString())
                    .brand(findProduct.getBrand())
                    .title(findProduct.getTitle())
                    .discountRate(findProduct.getDiscountRate())
                    .price(Integer.toString(findProduct.getPrice()))
                    .badges(findProduct.getBadges())
                    .liked(ifILike(userId,findProduct))
                    .build();
            getRecommendDtos.add(getRecommendDto);
        }
        return getRecommendDtos;
    }
    boolean ifILike(Long userId,Product product){
        Optional<Member> member = memberRepository.findByIdFetchLikes(userId);
        if(!member.isPresent()){
            return false;
        }
        if(member.get().getProductLikes().contains(product)){
            return true;
        }
        return false;

    }
    public GetProductDetailDto getProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(()->new IllegalArgumentException());
        List<String> thumbnails = new ArrayList<>();
        thumbnails.add(product.getThumbnail());
        GetProductDetailDto getProductDetailDto = GetProductDetailDto.builder()
                .price(product.getPrice())
                .title(product.getTitle())
                .summary(product.getDescription())
                .liked(product.getLikeCount())
                .badges("NEW")
                .thumbnail(thumbnails)
                .category(product.getCategory().toString())
                .build();
        return getProductDetailDto;
    }


    private GetProductDto makeProductForm(Product product) {
        GetProductDto getProductDto = GetProductDto.builder()
                .id(product.getId())
                .image(product.getThumbnail())
                .brand(product.getBrand())
                .price(product.getPrice())
                .title(product.getTitle())
                .build();
        return getProductDto;
    }
}
