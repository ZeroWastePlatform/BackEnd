package com.greenUs.server.post.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.attachment.repository.AttachmentRepository;
import com.greenUs.server.hashtag.service.HashtagService;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.domain.Recommend;
import com.greenUs.server.post.dto.PostPopularityResponseDto;
import com.greenUs.server.post.dto.PostRecommendationResponseDto;
import com.greenUs.server.post.dto.PostRequestDto;
import com.greenUs.server.post.dto.PostResponseDto;
import com.greenUs.server.post.repository.PostRepository;
import com.greenUs.server.post.repository.RecommendRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

	private static final int PAGE_POST_COUNT = 6;
	private static final int PAGE_SEARCH_POST_COUNT = 10;
	private final PostRepository postRepository;
	private final MemberRepository memberRepository;
	private final RecommendRepository recommendRepository;
	private final HashtagService hashtagService;
	private final AttachmentRepository attachmentRepository;

	// 게시글 목록 조회
	public Page<PostResponseDto> getPostLists(Integer kind, Integer page, String orderCriteria) {

		/* 게시판 종류(kind), 정렬 조건(orderCriteria)에 따라 게시판 내용물을 불러온 후 반환
		 *  게시판 종류(kind) -> 1: 자유게시판, 2: 정보공유, 3: 중고거래
		 *  정렬 조건(orderCriteria) -> createdAt: 최신순, viewCnt: 조회순, recommendCnt: 추천순 */

		PageRequest pageRequest = PageRequest.of(page, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, orderCriteria));

		return postRepository.findByKind(kind, pageRequest)
			.map(PostResponseDto::new);
	}


	public Page<PostResponseDto> getSearchLists(String word, Integer page) {

		PageRequest pageRequest = PageRequest.of(page, PAGE_SEARCH_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdAt"));

		return postRepository.findByTitleContainingOrContentContaining(word, word, pageRequest)
			.map(PostResponseDto::new);
	}

	// 게시글 내용 불러오기
	public PostResponseDto getPostDetail(Long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		return new PostResponseDto(post);
	}

	// 게시글 작성
	@Transactional
	public Integer setPostWriting(PostRequestDto postRequestDto) {

		// 게시글 저장
		Post post = postRepository.save(postRequestDto.toEntity());

		// 해시태그 저장
		if (!postRequestDto.getHashtag().isEmpty())
			hashtagService.applyHashtag(post, postRequestDto.getHashtag());

		return new PostResponseDto(post).getKind();
	}

	// 게시글 수정
	@Transactional
	public Integer setPostModification(Long id, PostRequestDto postRequestDto) {

		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		post.update(
			postRequestDto.getKind(),
			postRequestDto.getTitle(),
			postRequestDto.getContent(),
			postRequestDto.getPrice()
		);

		if (!postRequestDto.getHashtag().isEmpty())
			hashtagService.applyHashtag(post, postRequestDto.getHashtag());

		return new PostResponseDto(post).getKind();
	}

	// 게시글 삭제(수정 필요 - 작성자 확인, 댓글 있는지 확인(댓글 있으면 삭제 X)
	@Transactional
	public Integer setPostDeletion(Long id) {

		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		postRepository.delete(post);
		return post.getKind();
	}

	// 조회수 증가
	@Transactional
	public void updateViewCnt(Long id) {

		postRepository.updateViewCnt(id);
	}

	// 추천수 증가
	@Transactional
	public void setPostRecommendation(Long id) {

		Post post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		// 유저 관련처리 지금 생략
		Member member = memberRepository.findById(1l).get();

		if (recommendRepository.findByPostAndMember(post, member) == null) {
			recommendRepository.save(new Recommend(post, member));
			return;
		}

		Recommend recommend = recommendRepository.findByPostAndMember(post, member);
		recommend.cancleRecommend(post);
		recommendRepository.delete(recommend);
	}

	// 당일 인기 게시글 3개
	public List<PostPopularityResponseDto> getPopularityPost() {

		LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0));
		LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
		List<Post> posts = postRepository.findTop3ByCreatedAtBetweenOrderByRecommendCntDesc(startDatetime, endDatetime);

		List<PostPopularityResponseDto> postPopularityResponseDto = new ArrayList<>();

		for (Post post : posts) {
			postPopularityResponseDto.add(new PostPopularityResponseDto(post));
		}

		return postPopularityResponseDto;
	}

	public List<PostRecommendationResponseDto> getRecommendationPost(Integer kind) {

		List<Post> posts = postRepository.findTop3ByKindOrderByRecommendCntDesc(kind);

		List<PostRecommendationResponseDto> postRecommendationResponseDto = new ArrayList<>();

		for (Post post : posts) {
			postRecommendationResponseDto.add(new PostRecommendationResponseDto(post));
		}

		return postRecommendationResponseDto;
	}
}