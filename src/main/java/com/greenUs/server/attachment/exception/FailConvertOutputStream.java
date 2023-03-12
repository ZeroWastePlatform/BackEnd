package com.greenUs.server.attachment.exception;

public class FailConvertOutputStream extends RuntimeException {

	public FailConvertOutputStream(String message) {
		super(message);
	}

	public FailConvertOutputStream() {
		this("Output Stream 변환에 실패했습니다.");
	}
}