package com.greenUs.server.member.service;

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
import com.greenUs.server.member.dto.response.MemberResponse;
import com.greenUs.server.member.dto.response.MyPageCommunityResponse;
import com.greenUs.server.member.dto.response.MyPageContentResponse;
import com.greenUs.server.member.dto.response.MyPageProfileResponse;
import com.greenUs.server.member.dto.response.MyPagePurchaseResponse;
import com.greenUs.server.member.exception.NotFoundMemberException;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.post.dto.response.MyPageCommentResponse;
import com.greenUs.server.post.dto.response.MyPagePostResponse;
import com.greenUs.server.post.repository.PostRepository;
import com.greenUs.server.purchase.dto.response.PurchaseResponse;
import com.greenUs.server.purchase.repository.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final PurchaseRepository purchaseRepository;
	private final CouponRepository couponRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final BookmarkRepository bookmarkRepository;

	public MemberResponse findById(Long id) {
		Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
		return new MemberResponse(member);
	}

	public MyPagePurchaseResponse getMyOrder(MemberResponse member, int page) {

		PageRequest pageRequest = PageRequest.of(page, 10);
		Page<PurchaseResponse> purchaseResponses = purchaseRepository.findByMemberId(member.getId(), pageRequest);
		return new MyPagePurchaseResponse(
			new MyPageProfileResponse(
					member.getId(),
					member.getName(),
					member.getNickname(),
					member.getLevel(),
					0, // 찜은 추후 구현
					member.getPoint(),
					couponRepository.findCountByMemberId(member.getId())
			),
			purchaseResponses
		);
	}

	public MyPageCommunityResponse getMyCommunity(MemberResponse member, String kind, int page) {
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

	public Page<MyPageContentResponse> getMyContents(MemberResponse member, int page) {
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
}
