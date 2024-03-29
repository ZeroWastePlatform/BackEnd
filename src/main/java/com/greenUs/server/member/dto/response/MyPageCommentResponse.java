package com.greenUs.server.member.dto.response;

import com.greenUs.server.comment.domain.Comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageCommentResponse {

	@Schema(description = "댓글 번호", nullable = false, example = "47")
	private Long id;

	@Schema(description = "댓글 작성한 게시글 번호", nullable = false, example = "22")
	private Long postId;

	@Schema(description = "게시판 구분(1: 자유게시판, 2: 정보 공유, 3: 중고 거래)", nullable = false, allowableValues = {"1", "2", "3"})
	private Integer kind;

	@Schema(description = "게시판 제목", nullable = false, example = "게시글 제목 입니다.")
	private String postTitle;

	@Schema(description = "댓글 내용", nullable = false)
	private String content;

	@Schema(description = "게시판 댓글수", example = "14", nullable = false)
	private Integer replyCnt;

	public MyPageCommentResponse(Comment entity) {
		this.id = entity.getId();
		this.postId = entity.getPost().getId();
		this.kind = entity.getPost().getKind();
		this.postTitle = entity.getPost().getTitle();
		this.content = entity.getContent();
		this.replyCnt = entity.getPost().getComments().size();
	}
}
