package com.greenUs.server.attachment.exception;

public class FailResizeAttachment extends RuntimeException {

	public FailResizeAttachment(String message) {
		super(message);
	}

	public FailResizeAttachment() {
		this("파일 리사이즈에 실패했습니다.");
	}
}