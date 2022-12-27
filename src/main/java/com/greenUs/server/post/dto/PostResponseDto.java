package com.greenUs.server.post.dto;

import com.greenUs.server.member.domain.Member;
import com.greenUs.server.post.domain.Post;

import lombok.Getter;

@Getter
public class PostResponseDto {

	private Long id;
	private Integer kind;
	private String title;
	private Member member;
	private String content;
	private Integer price;
	private Integer view_cnt;
	private Integer reply_cnt;
	private Integer recommend_cnt;

	public PostResponseDto(Post entity) {
		this.id = entity.getId();
		this.kind = entity.getKind();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.price = entity.getPrice();
		this.view_cnt = entity.getView_cnt();
		this.reply_cnt = entity.getReply_cnt();
		this.recommend_cnt = entity.getRecommend_cnt();
	}
}
