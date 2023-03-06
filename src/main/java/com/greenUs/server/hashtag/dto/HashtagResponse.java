package com.greenUs.server.hashtag.dto;

import java.util.ArrayList;
import java.util.List;

import com.greenUs.server.hashtag.domain.Keyword;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HashtagResponse {

	@Schema(description = "게시판 해시태그", example = "[그리너스, 지구]", nullable = false)
	private List<String> hashtags = new ArrayList<>();

	public HashtagResponse(List<Keyword> keywords) {

		for (Keyword keyword : keywords) {
			hashtags.add(keyword.getContent());
		}
	}
}
