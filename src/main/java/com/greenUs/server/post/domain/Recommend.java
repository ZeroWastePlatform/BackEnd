package com.greenUs.server.post.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.member.domain.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recommend extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "receommend_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(nullable = false)
	private boolean status; // true : 좋아요, false : 좋아요 취소

	public Recommend(Post post, Member member) {
		this.status = true;
		this.post = post;
		this.member = member;
		post.caculateRecommendCnt(post.getRecommendCnt() + 1);
	}

	public void cancleRecommend(Post post) {
		this.status = false;
		post.caculateRecommendCnt(post.getRecommendCnt() - 1);
	}
}
