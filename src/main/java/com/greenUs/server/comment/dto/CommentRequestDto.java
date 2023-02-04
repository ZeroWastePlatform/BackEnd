package com.greenUs.server.comment.dto;

import com.greenUs.server.comment.domain.Comment;
import com.greenUs.server.post.domain.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {

	@Schema(description = "게시글 번호", nullable = false)
	private Long postId;

	@Schema(description = "댓글 내용", nullable = false)
	private String content;

	// public Comment toEntity() {
	// 	return Comment.builder()
	// 		.content(content)
	// 		.build();
	// }
}
