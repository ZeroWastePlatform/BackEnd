package com.greenUs.server.hashtag.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.greenUs.server.hashtag.domain.Hashtag;
import com.greenUs.server.hashtag.domain.Keyword;
import com.greenUs.server.hashtag.repository.HashtagRepository;
import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.dto.PostRequestDto;
import com.greenUs.server.post.dto.PostResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagService {

	private final HashtagRepository hashtagRepository;
	private final KeywordService keywordService;

	// 입력 받은 해시태그 문자열 들을 변환 후 차례대로 키워드에 저장
	public void applyHashtag(Post post, String keywordContentsStr) {

		// 해시태그 문자열 정리('#' 구별 -> 공백 제거 -> 빈 문자열 제거 -> 리스트 변환)
		List<String> keywordContents = Arrays.stream(keywordContentsStr.split("#"))
				.map(String::trim)
				.filter(s -> s.length() > 0)
				.collect(Collectors.toList());

		// 키워드 들을 차례대로 저장하는 saveHashtag() 호출
		keywordContents.forEach(keywordContent -> {
			setHashtag(post, keywordContent);
		});
	}

	// 키워드 저장 후 해시태그 내에 정보 확인 후 내용이 있다면 바로 리턴, 내용이 없다면 저장 후 리턴
	private Hashtag setHashtag(Post post, String keywordContent) {

		Keyword keyword = keywordService.setKeyword(keywordContent);

		// 이미 해시태그 내에 게시판ID와 키워드ID가 존재 한다면 바로 리턴
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
}
