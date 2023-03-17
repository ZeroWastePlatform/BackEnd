package com.greenUs.server.post.dto.response;

import com.greenUs.server.post.domain.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PostRecommendationResponse {

	@Schema(description = "게시판 번호", nullable = false)
	private Long id;

	@Schema(description = "게시판 구분(1: 자유게시판, 2: 정보 공유, 3: 중고 거래)", nullable = false)
	private Integer kind;

	@Schema(description = "게시판 제목", nullable = false)
	private String title;

	@Schema(description = "게시판 내용", nullable = false)
	private String content;

	public PostRecommendationResponse(Post entity) {
		this.id = entity.getId();
		this.kind = entity.getKind();
		this.title = entity.getTitle();
		this.content = entity.getContent();
	}
}