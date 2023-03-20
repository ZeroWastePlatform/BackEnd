package com.greenUs.server.comment.exception;

public class NotEqualCommentAndPostComment extends RuntimeException {

	public NotEqualCommentAndPostComment(String message) {
		super(message);
	}

	public NotEqualCommentAndPostComment() {
		this("해당하는 게시글의 댓글 번호가 아닙니다.");
	}
}