package com.greenUs.server.post.dto;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.post.domain.Post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {

	private Long id;
	private Integer kind;
	private String title;
	private Member member;
	private String content;
	private Integer price;
	private Integer viewCnt;
	private Integer replyCnt;
	private Integer recommendCnt;

	public PostResponseDto(Post entity) {
		this.id = entity.getId();
		this.kind = entity.getKind();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.price = entity.getPrice();
		this.viewCnt = entity.getViewCnt();
		this.replyCnt = entity.getReplyCnt();
		this.recommendCnt = entity.getRecommendCnt();
	}
}
