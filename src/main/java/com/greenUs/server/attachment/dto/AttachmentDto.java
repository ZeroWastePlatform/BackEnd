package com.greenUs.server.attachment.dto;

import org.springframework.web.multipart.MultipartFile;

import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.post.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDto {

	private Post post;

	private String originalFileName; // 원본 파일 이름

	private String storedFileName; // 서버 저장용 파일 이름(원본 파일이 같은 이름으로 여러개 올라온다면 서버에서 구별하기 힘드므로 서버 저장용 파일 이름 구분)

	public Attachment toEntity() {
		return Attachment.builder()
			.post(post)
			.originalFileName(originalFileName)
			.storedFileName(storedFileName)
			.build();
	}
}
