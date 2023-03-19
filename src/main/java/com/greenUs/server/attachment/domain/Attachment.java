package com.greenUs.server.attachment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.post.domain.Post;
import com.greenUs.server.product.domain.Product;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class Attachment extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "attachment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
	@JoinColumn(name = "post_id")
	private Post post;

	private String originalFileName;

	private String serverFileName;

	private String thumbnailFileName;

	private String serverFileUrl;

	private String thumbnailFileUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	@Builder
	public Attachment(Post post, String originalFileName, String serverFileName, String thumbnailFileName,
		String serverFileUrl, String thumbnailFileUrl) {
		this.post = post;
		this.originalFileName = originalFileName;
		this.serverFileName = serverFileName;
		this.thumbnailFileName = thumbnailFileName;
		this.serverFileUrl = serverFileUrl;
		this.thumbnailFileUrl = thumbnailFileUrl;
	}
}
