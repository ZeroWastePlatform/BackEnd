package com.greenUs.server.hashtag.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.greenUs.server.hashtag.domain.Keyword;
import com.greenUs.server.hashtag.repository.KeywordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeywordService {

	private final KeywordRepository keywordRepository;

	// 키워드를 저장하는데 이미 키워드 목록에 있다면 바로 리턴하고, 키워드 목록에 없다면 저장 후 리턴
	public Keyword setKeyword(String keywordContent) {

		// 이미 있는 키워드는 중복 X, 바로 리턴
		Optional<Keyword> optKeyword = keywordRepository.findByContent(keywordContent);
		if (optKeyword.isPresent()) {
			return optKeyword.get();
		}

		// 키워드가 없다면 키워드 저장 후 리턴
		Keyword keyword = Keyword.builder()
			.content(keywordContent)
			.build();

		keywordRepository.save(keyword);

		return keyword;
	}
}
