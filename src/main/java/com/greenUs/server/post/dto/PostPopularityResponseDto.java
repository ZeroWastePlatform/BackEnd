package com.greenUs.server.post.dto;

import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.hashtag.domain.Hashtag;
import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.domain.Time;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class PostPopularityResponseDto {

	@Schema(description = "게시판 번호", nullable = false, example = "47")
	private Long id;

	@Schema(description = "게시판 제목", nullable = false)
	private String title;

	@Schema(description = "게시판 추천수", example = "17", nullable = false)
	private Integer recommendCnt;

	public PostPopularityResponseDto(Post entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.recommendCnt = entity.getRecommendCnt();
	}
}
