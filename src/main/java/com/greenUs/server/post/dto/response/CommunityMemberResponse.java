package com.greenUs.server.post.dto.response;

import com.greenUs.server.comment.domain.Comment;
import com.greenUs.server.post.domain.Post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommunityMemberResponse {

	@Schema(description = "게시판 작성자 번호", nullable = false, example = "47")
	private Long memberId;

	@Schema(description = "게시판 작성자 닉네임", nullable = false)
	private String nickname;

	public CommunityMemberResponse(Post entity) {
		this.memberId = entity.getMember().getId();
		this.nickname = entity.getMember().getNickname();
	}

	public CommunityMemberResponse(Comment entity) {
		this.memberId = entity.getMember().getId();
		this.nickname = entity.getMember().getNickname();
	}
}
