package com.greenUs.server.comment.dto;

import com.greenUs.server.comment.domain.Comment;
import com.greenUs.server.post.dto.response.CommunityMemberResponse;

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

	@Schema(description = "댓글 작성자 닉네임", nullable = false)
	private CommunityMemberResponse commentMember;

	@Schema(description = "댓글 내용", nullable = false)
	private String content;

	public CommentResponse(Comment entity) {
		this.id = entity.getId();
		this.commentMember = new CommunityMemberResponse(entity);
		this.content = entity.getContent();
	}
}
