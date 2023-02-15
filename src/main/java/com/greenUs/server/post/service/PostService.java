package com.greenUs.server.post.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.greenUs.server.attachment.AttachmentDto;
import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.attachment.repository.AttachmentRepository;
import com.greenUs.server.hashtag.service.HashtagService;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.member.domain.SocialType;
import com.greenUs.server.member.repository.MemberRepository;
import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.domain.Recommend;
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

		/* 넘겨받은 orderCriteria 를 이용해 내림차순하여 PageRequest 객체 반환
		 *  PageRequest는 Pageable 인터페이스를 구현한 구현체 */
		PageRequest pageRequest = PageRequest.of(page, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, orderCriteria));

		// 게시판 종류(kind)에 해당하는 post 페이지 객체 반환
		Page<Post> post = postRepository.findByKind(kind, 2, pageRequest);

		// 람다식을 활용하여 간단히 DTO로 변환
		Page<PostResponseDto> postResponseDto = post.map(PostResponseDto::new);

		return postResponseDto;

	}

	// 게시글 검색 목록 조회
	public Page<PostResponseDto> getPostSearchLists(Integer kind, Integer page, String orderCriteria, String searchCondition, String searchKeyword) {

		PageRequest pageRequest = PageRequest.of(page, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, orderCriteria));

		Page<Post> post = null;

		if (searchCondition.equals("title")) {
			post = postRepository.findByKindAndTitleContaining(kind, searchKeyword, pageRequest);
		}
		else if (searchCondition.equals("content")) {
			post = postRepository.findByKindAndContentContaining(kind, searchKeyword, pageRequest);
		}
		else if (searchCondition.equals("hashtag")) {
			post = postRepository.findByKindAndHashtagContaining(kind, searchKeyword, pageRequest);
		}

		Page<PostResponseDto> postResponseDto = post.map(PostResponseDto::new);

		return postResponseDto;
	}

	// 게시글 내용 불러오기
	public PostResponseDto getPostDetail(Long id) {

		Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		return new PostResponseDto(post);
	}

	// 게시글 작성
	@Transactional
	public Integer setPostWriting(List<MultipartFile> postFiles, PostRequestDto postRequestDto) throws IOException {

		Post post;

		// 파일 첨부 여부에 따라 로직 분리
		if (postFiles.isEmpty()) {

			// 1. 파일이 없는 경우
			post = postRepository.save(postRequestDto.toEntity());
			hashtagService.applyHashtag(post, postRequestDto.getHashtag());

			return new PostResponseDto(post).getKind();
		}

		// 2. 파일이 있는 경우
		/*
			1. DTO에 담긴 파일을 꺼냄
			2. 파일의 이름을 가져옴
			3. 서버 저장용 이름을 만듦
			4. 저장 경로 설정
			5. 해당 경로에 파일 저장
			6. post table에 데이터 save
			7. attachment table에 데이터 save
		 */

		post = postRepository.save(postRequestDto.toFileSaveEntity());

		// 해시태그 저장
		hashtagService.applyHashtag(post, postRequestDto.getHashtag());

		Long saveId = post.getId(); // 부모(post) 번호

		// DB 저장 후 생성어진 ID 값을 불러오기 위해 Post 다시 호출
		Post fileSavePost = postRepository.findById(saveId).get();

		for (MultipartFile postFile : postFiles) {

			String originalFilename = postFile.getOriginalFilename();

			String storedFileName = System.currentTimeMillis() + " " + originalFilename;

			String savePath = "C:/springboot_img/" + storedFileName;

			postFile.transferTo(new File(savePath));

			// AttachmentDto를 통해 Attachment Entity로 변환
			Attachment attachment = new AttachmentDto(fileSavePost, originalFilename, storedFileName).toEntity();

			// 첨부파일 Entity에 내용 저장
			attachmentRepository.save(attachment);
		}

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
}