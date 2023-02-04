package com.greenUs.server.comment.dto;

import com.greenUs.server.comment.domain.Comment;
import com.greenUs.server.member.domain.Member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CommentResponseDto {

	@Schema(description = "댓글 번호", nullable = false, example = "47")
	private Long id;

	@Schema(description = "댓글 작성자", nullable = false)
	private Member member;

	@Schema(description = "댓글 내용", nullable = false)
	private String content;

	public CommentResponseDto(Comment entity) {
		this.id = entity.getId();
		this.member = entity.getMember();
		this.content = entity.getContent();
	}
}
