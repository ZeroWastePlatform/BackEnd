package com.greenUs.server.attachment.exception;

public class NotEqualAttachmentAndPostAttachment extends RuntimeException {

	public NotEqualAttachmentAndPostAttachment(String message) {
		super(message);
	}

	public NotEqualAttachmentAndPostAttachment() {
		this("게시글의 첨부파일 이름이 아닙니다.");
	}
}