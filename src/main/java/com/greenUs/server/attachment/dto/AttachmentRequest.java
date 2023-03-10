package com.greenUs.server.attachment.dto;

import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.post.domain.Post;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AttachmentRequest {

	private Post post;

	private String originalFileName;

	private String storedFileName;

	public Attachment toEntity() {
		return Attachment.builder()
			.post(post)
			.originalFileName(originalFileName)
			.storedFileName(storedFileName)
			.build();
	}
}
