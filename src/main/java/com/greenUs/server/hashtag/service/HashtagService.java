package com.greenUs.server.hashtag.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.hashtag.domain.Hashtag;
import com.greenUs.server.hashtag.domain.Keyword;
import com.greenUs.server.hashtag.dto.HashtagResponseDto;
import com.greenUs.server.hashtag.repository.HashtagRepository;
import com.greenUs.server.hashtag.repository.KeywordRepository;
import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.dto.PostResponseDto;
import com.greenUs.server.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagService {

	private static final int PAGE_POST_COUNT = 6;
	private final PostRepository postRepository;
	private final HashtagRepository hashtagRepository;
	private final KeywordRepository keywordRepository;
	private final KeywordService keywordService;

	// 게시판 정보와 키워드 들을 입력받아 관련 내용 저장 또는 수정
	@Transactional
	public void applyHashtag(Post post, String keywordContentsStr) {

		// 기존 해시태그 정보 가져오기
		List<Hashtag> oldHashtagList = getHashtags(post);

		// 입력받은 키워드 문자열 정리('#' 구별 -> 공백 제거 -> 빈 문자열 제거 -> 리스트 변환)
		List<String> keywordContents = Arrays.stream(keywordContentsStr.split("#"))
			.map(String::trim)
			.filter(s -> s.length() > 0)
			.collect(Collectors.toList());

		// 삭제할 키워드 정보를 저장하는 리스트 생성
		List<Hashtag> needToDelete = new ArrayList<>();

		// 새롭게 추가된 키워드 들을 차례대로 저장하는 saveHashtag() 호출
		keywordContents.forEach(keywordContent -> {
			setHashtag(post, keywordContent);
		});

		// 삭제할 키워드 정보들을 삭제
		needToDelete.forEach(hashtag -> {
			hashtagRepository.delete(hashtag);
		});
	}

	// 키워드 저장 후 해시태그 내에 게시판ID와 키워드ID정보가 있다면 바로 리턴, 내용이 없다면 게시판ID와 키워드ID 저장
	@Transactional
	public Hashtag setHashtag(Post post, String keywordContent) {

		// 키워드 저장
		Keyword keyword = keywordService.setKeyword(keywordContent);

		// 해시태그 내에 게시판ID와 키워드ID가 존재 한다면 바로 리턴
		Optional<Hashtag> optHashtag = hashtagRepository.findByPostIdAndKeywordId(post.getId(), keyword.getId());

		if (optHashtag.isPresent()) {
			return optHashtag.get();
		}

		// 해시태그 내에 게시판ID와 키워드ID가 존재하지 않으면 관련 내용 저장 후 리턴
		Hashtag hashtag = Hashtag.builder()
			.post(post)
			.keyword(keyword)
			.build();

		hashtagRepository.save(hashtag);

		return hashtag;
	}

	// 해시태그 정보를 불러오기
	@Transactional
	public List<Hashtag> getHashtags(Post post) {

		return hashtagRepository.findAllByPostId(post.getId());
	}

	// 해시태그 인기글
	public HashtagResponseDto getPopularityKeyword() {

		List<Keyword> keywords = keywordRepository.findTop5ByOrderByCountDesc();

		HashtagResponseDto hashtagResponseDto = new HashtagResponseDto(keywords);

		return hashtagResponseDto;
	}

	// 해시태그 검색
	public Page<PostResponseDto> getPostSearchList(Integer page, String keyword, String orderCriteria) {

		PageRequest pageRequest = PageRequest.of(page, PAGE_POST_COUNT, Sort.by(Sort.Direction.DESC, orderCriteria));

		Page<Post> post = postRepository.findByKeywordContaining(keyword, pageRequest);

		Page<PostResponseDto> postResponseDto = post.map(PostResponseDto::new);

		return postResponseDto;
	}
}
