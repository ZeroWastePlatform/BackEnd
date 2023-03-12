package com.greenUs.server.post.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.DynamicUpdate;

import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.comment.domain.Comment;
import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.hashtag.domain.Hashtag;
import com.greenUs.server.member.domain.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@DynamicUpdate
@Entity
public class Post extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "post_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(nullable = false)
	private Integer kind;

	@Column(length = 50, nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	private Integer price;

	private Integer viewCnt;

	private Integer recommendCnt;

	@OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private List<Hashtag> hashtags = new ArrayList<>();

	private Boolean fileAttached;

	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
	private List<Attachment> attachments = new ArrayList<>();

	@Builder
	public Post(Member member, Integer kind, String title, String content, Integer price, Boolean fileAttached) {
		this.member = member;
		this.kind = kind;
		this.title = title;
		this.content = content;
		this.price = price;
		this.fileAttached = fileAttached;
	}

	public void update(Integer kind, String title, String content, Integer price, Boolean fileAttached) {
		this.kind = kind;
		this.title = title;
		this.content = content;
		this.price = price;
		this.fileAttached = fileAttached;
	}

	public void caculateRecommendCnt(Integer recommendCnt) {
		this.recommendCnt = recommendCnt;
	}

	@PrePersist
	public void prePersist() {
		this.viewCnt = this.viewCnt == null ? 0 : this.viewCnt;
		this.recommendCnt = this.recommendCnt == null ? 0 : this.recommendCnt;
		this.fileAttached = this.fileAttached == null ? false : this.fileAttached;
	}
}
