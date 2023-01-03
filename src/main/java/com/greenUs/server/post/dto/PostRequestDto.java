package com.greenUs.server.post.dto;

import com.greenUs.server.post.domain.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString // 컨트롤러 인자 테스트용
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {

	@Schema(description = "게시판 구분(1: 자유게시판, 2: 정보 공유, 3: 중고 거래", nullable = false, allowableValues = {"1", "2", "3"})
	private Integer kind;

	@Schema(description = "게시판 제목", nullable = false)
	private String title;

	@Schema(description = "게시판 내용", nullable = false)
	private String content;

	@Schema(description = "게시판 가격(중고 거래 게시판일 경우)", example = "20000", nullable = true)
	private Integer price;

	public Post toEntity() {
		return Post.builder()
			.kind(kind)
			.title(title)
			.content(content)
			.price(price)
			.build();
	}
}


