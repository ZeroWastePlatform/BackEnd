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
import com.greenUs.server.post.dto.PostRecommendationResponse;
import com.greenUs.server.post.dto.PostRequest;
import com.greenUs.server.post.dto.PostResponse;
import com.greenUs.server.post.exception.NotEqualMemberAndPostMember;
import com.greenUs.server.post.exception.NotFoundPostException;
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
	private final RecommendRepository recommendRepository;
	private final HashtagService hashtagService;
	private final AttachmentRepository attachmentRepository;

	public Page<PostResponse> getPostLists(Integer kind, Integer page, String orderCriteria) {

		PageRequest pageRequest = PageRequest.of(page, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, orderCriteria));

		return postRepository.findByKind(kind, pageRequest)
			.map(PostResponse::new);
	}

	public Page<PostResponse> getSearchPostLists(String word, Integer page) {

		PageRequest pageRequest = PageRequest.of(page, PAGE_SEARCH_POST_COUNT, Sort.by(Sort.Direction.DESC, "createdAt"));

		return postRepository.findByTitleContainingOrContentContaining(word, word, pageRequest)
			.map(PostResponse::new);
	}

	public PostResponse getPostDetail(Long id) {

		Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);

		return new PostResponse(post);
	}

	@Transactional
	public Integer createPost(PostRequest postRequestDto) {

		Post post = postRepository.save(postRequestDto.toEntity());

		if (!postRequestDto.getHashtag().isEmpty())
			hashtagService.applyHashtag(post, postRequestDto.getHashtag());

		return new PostResponse(post).getKind();
	}

	@Transactional
	public Integer updatePost(Long id, PostRequest postRequestDto) {

		Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);

		if (!post.getId().equals(postRequestDto.getMember().getId())) {
			throw new NotEqualMemberAndPostMember();
		}

		post.update(
			postRequestDto.getKind(),
			postRequestDto.getTitle(),
			postRequestDto.getContent(),
			postRequestDto.getPrice()
		);

		if (!postRequestDto.getHashtag().isEmpty())
			hashtagService.applyHashtag(post, postRequestDto.getHashtag());

		return new PostResponse(post).getKind();
	}

	@Transactional
	public Integer deletePost(Long id, Member member) {

		Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);

		if (!post.getId().equals(member.getId())) {
			throw new NotEqualMemberAndPostMember();
		}

		postRepository.delete(post);
		return post.getKind();
	}

	@Transactional
	public void updateViewCnt(Long id) {

		postRepository.updateViewCnt(id);
	}

	@Transactional
	public void updateRecommendationCnt(Long id, Member member) {

		Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);

		if (recommendRepository.findByPostAndMember(post, member) == null) {
			recommendRepository.save(new Recommend(post, member));
			return;
		}

		Recommend recommend = recommendRepository.findByPostAndMember(post, member);
		recommend.cancleRecommend(post);
		recommendRepository.delete(recommend);
	}

	public List<PostResponse> getPopularPosts() {

		LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0));
		LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));
		List<Post> posts = postRepository.findTop3ByCreatedAtBetweenOrderByRecommendCntDesc(startDatetime, endDatetime);

		List<PostResponse> postResponseDto = new ArrayList<>();

		for (Post post : posts) {
			postResponseDto.add(new PostResponse(post));
		}

		return postResponseDto;
	}

	public List<PostRecommendationResponse> getRecommendedPosts(Integer kind) {

		List<Post> posts = postRepository.findTop3ByKindOrderByRecommendCntDesc(kind);

		List<PostRecommendationResponse> postRecommendationResponseDto = new ArrayList<>();

		for (Post post : posts) {
			postRecommendationResponseDto.add(new PostRecommendationResponse(post));
		}

		return postRecommendationResponseDto;
	}
}