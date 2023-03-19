package com.greenUs.server.post.dto.response;

import com.greenUs.server.post.domain.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRecommendationResponse {

	@Schema(description = "게시판 번호", nullable = false)
	private Long id;

	@Schema(description = "게시판 제목", nullable = false)
	private String title;

	@Schema(description = "게시판 내용", nullable = false)
	private String content;

	public PostRecommendationResponse(Post entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
	}
}