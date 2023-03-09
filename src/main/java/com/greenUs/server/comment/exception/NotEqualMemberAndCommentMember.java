package com.greenUs.server.comment.exception;

public class NotEqualMemberAndCommentMember extends RuntimeException {

	public NotEqualMemberAndCommentMember(String message) {
		super(message);
	}

	public NotEqualMemberAndCommentMember() {
		this("댓글의 작성자가 아닙니다.");
	}
}