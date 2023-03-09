package com.greenUs.server.post.exception;

public class NotEqualMemberAndPostMember extends RuntimeException {

	public NotEqualMemberAndPostMember(String message) {
		super(message);
	}

	public NotEqualMemberAndPostMember() {
		this("게시글의 작성자가 아닙니다.");
	}
}