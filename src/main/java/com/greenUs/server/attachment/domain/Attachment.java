package com.greenUs.server.attachment.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.post.domain.Post;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class Attachment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "attachment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
	@ToString.Exclude
    @JoinColumn(name = "post_id")
    private Post post;

    private String originalFileName;

    private String storedFileName;

	private String attachmentUrl;

	@Builder
	public Attachment(Post post, String originalFileName, String storedFileName, String attachmentUrl) {
		this.post = post;
		this.originalFileName = originalFileName;
		this.storedFileName = storedFileName;
		this.attachmentUrl = attachmentUrl;
	}
}
