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
public class CommentResponse {

	@Schema(description = "댓글 번호", nullable = false, example = "47")
	private Long id;

	@Schema(description = "게시판 구분(1: 자유게시판, 2: 정보 공유, 3: 중고 거래)", nullable = false, allowableValues = {"1", "2", "3"})
	private Integer kind;

	@Schema(description = "댓글 작성자", nullable = false)
	private Member member;

	@Schema(description = "댓글 내용", nullable = false)
	private String content;

	@Schema(description = "게시판 댓글수", example = "14", nullable = false)
	private Integer replyCnt;

	public CommentResponse(Comment entity) {
		this.id = entity.getId();
		this.kind = entity.getPost().getKind();
		this.member = entity.getMember();
		this.content = entity.getContent();
		this.replyCnt = entity.getPost().getComments().size();
	}
}
