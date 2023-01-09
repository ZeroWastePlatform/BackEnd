package com.greenUs.server.hashtag.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.greenUs.server.hashtag.domain.Hashtag;
import com.greenUs.server.hashtag.domain.Keyword;
import com.greenUs.server.hashtag.repository.HashtagRepository;
import com.greenUs.server.post.domain.Post;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagService {

	private final HashtagRepository hashtagRepository;
	private final KeywordService keywordService;

	// 게시판 정보와 키워드 들을 입력받아 관련 내용 저장 또는 수정
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

		// 삭제할 키워드 계산
		for ( Hashtag oldHashtag : oldHashtagList ) {

			boolean contains = keywordContents.stream().anyMatch(s -> s.equals(oldHashtag.getKeyword().getContent()));

			if ( contains == false ) {
				needToDelete.add(oldHashtag);
			}

		}

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
	private Hashtag setHashtag(Post post, String keywordContent) {

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

	// 게시판에 해당하는 키워드들 가져오기
	public List<String> getHashtagList(Post post) {
		List<Hashtag> hashtags = hashtagRepository.findAllByPostId(post.getId());
		List<String> hashtagList = new ArrayList<>();

		for (int i = 0; i < hashtags.size(); i++) {
			hashtagList.add(hashtags.get(i).getKeyword().getContent());
		}

		return hashtagList;
	}

	// 해시태그 정보를 불러오기
	public List<Hashtag> getHashtags(Post post) {

		return hashtagRepository.findAllByPostId(post.getId());
	}
}
