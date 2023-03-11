package com.greenUs.server.attachment.exception;

public class NotFoundObjectException extends RuntimeException {

	public NotFoundObjectException(String message) {
		super(message);
	}

	public NotFoundObjectException() {
		this("이미 삭제된 파일로 찾을 수 없습니다.");
	}
}