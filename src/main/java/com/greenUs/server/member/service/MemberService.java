package com.greenUs.server.member.service;

import com.greenUs.server.member.dto.response.*;
import com.greenUs.server.product.repository.ProductLikeRepository;
import com.greenUs.server.product.repository.ProductRepository;
import com.greenUs.server.purchase.domain.Purchase;
import com.greenUs.server.purchase.domain.PurchaseProduct;
import com.greenUs.server.purchase.repository.PurchaseProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.bookmark.domain.Bookmark;
import com.greenUs.server.bookmark.repository.BookmarkRepository;
import com.greenUs.server.comment.repository.CommentRepository;
import com.greenUs.server.coupon.repository.CouponRepository;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.dto.request.MemberRequest;
import com.greenUs.server.member.dto.request.SignUpRequest;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.member.dto.response.MyPageCommentResponse;
import com.greenUs.server.member.dto.response.MyPagePostResponse;
import com.greenUs.server.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private static final int PRODUCT_LIKE_COUNT = 8;

	private final MemberRepository memberRepository;
	private final CouponRepository couponRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final BookmarkRepository bookmarkRepository;
	private final ProductRepository productRepository;
	private final PurchaseProductRepository purchaseProductRepository;
	private final ProductLikeRepository productLikeRepository;

	public MyPageProfileResponse getMyProfile(Long id) {
		Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
		return new MyPageProfileResponse(
				member.getId(),
				member.getName(),
				member.getNickname(),
				member.getLevel(),
				0, // 찜은 추후 구현
				member.getPoint(),
				couponRepository.findCountByMemberId(member.getId())
		);
	}

	public List<MyPagePurchaseResponse> getMyPurchase(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
		List<MyPagePurchaseResponse> responses = new ArrayList<>();

		List<PurchaseProduct> purchaseProducts = purchaseProductRepository.findAllByMemberId(memberId);

		for (PurchaseProduct purchaseProduct : purchaseProducts) {

			MyPagePurchaseResponse product = MyPagePurchaseResponse.builder()
					.productId(purchaseProduct.getProduct().getId())
					.brand(purchaseProduct.getProduct().getBrand())
					.title(purchaseProduct.getProduct().getTitle())
					.thumbnail(purchaseProduct.getProduct().getThumbnail())
					.price(purchaseProduct.getProduct().getPrice())
					.count(purchaseProduct.getPurchaseCount())
					.build();
			responses.add(product);
		}
		return responses;
	}

	public MyPageCommunityResponse getMyCommunity(Long memberId, String kind, int page) {
		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
		PageRequest pageRequest = PageRequest.of(page, 8);

		MyPageCommunityResponse myPageCommunityResponse = new MyPageCommunityResponse(
			new MyPageProfileResponse(
					member.getId(),
					member.getName(),
					member.getNickname(),
					member.getLevel(),
					0, // 찜은 추후 구현
					member.getPoint(),
					couponRepository.findCountByMemberId(member.getId())
			)
		);

		if (kind.equals("내가 작성한 게시글")) {
			myPageCommunityResponse.setPostResponses(postRepository.findByMemberId(member.getId(), pageRequest)
				.map(MyPagePostResponse::new));
			return myPageCommunityResponse;
		} else if (kind.equals("내가 작성한 댓글")) {
			myPageCommunityResponse.setCommentResponses(commentRepository.findByMemberId(member.getId(), pageRequest)
				.map(MyPageCommentResponse::new));
			return myPageCommunityResponse;
		}
		return null;
	}

	public Page<MyPageContentResponse> getMyContents(Long memberId, int page) {
		Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
		PageRequest pageRequest = PageRequest.of(page, 10);
		Page<Bookmark> bookmarks = bookmarkRepository.findByMemberId(member.getId(), pageRequest);

		Page<MyPageContentResponse> myPageContentResponses = bookmarks.map(MyPageContentResponse::new);
		return myPageContentResponses;
	}

	@Transactional
	public MemberResponse updateMyInfo(Long id, MemberRequest memberRequest) {
		Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);

		member.changeInfo(
			memberRequest.getNickname(),
			memberRequest.getAddress(),
			memberRequest.getPhoneNum(),
			memberRequest.getInterestArea());
		return new MemberResponse(memberRepository.save(member));
	}

	@Transactional
	public MemberResponse signUp(Long id, SignUpRequest signUpRequest) {
		Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);

		member.changeNickName(signUpRequest.getNickName());
		return new MemberResponse(memberRepository.save(member));
	}

    public Page<MyPageLikeProductResponse> findLikeProducts(Long id, int page) {
		Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);

		PageRequest pageRequest = PageRequest.of(page, PRODUCT_LIKE_COUNT);
		List<Long> productList = new ArrayList<>();
		productLikeRepository.findByMember(member).stream().forEach(productLike -> {
			productList.add(productLike.getProduct().getId());
		});

		return productRepository.findByIdList(productList, pageRequest).map(product ->
				MyPageLikeProductResponse
						.builder()
						.id(product.getId())
						.brand(product.getBrand())
						.price(product.getPrice())
						.title(product.getTitle())
						.likeCount(product.getLikeCount())
						.discountRate(product.getDiscountRate())
						.build());
	}
}
