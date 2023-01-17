package com.greenUs.server.attachment.domain;

import java.security.cert.CertPathBuilder;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.post.domain.Post;

import javax.persistence.*;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Attachment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "attachment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String originalFileName;

    private String storedFileName;

	@Builder
	public Attachment(Post post, String originalFileName, String storedFileName) {
		this.post = post;
		this.originalFileName = originalFileName;
		this.storedFileName = storedFileName;
	}
}
