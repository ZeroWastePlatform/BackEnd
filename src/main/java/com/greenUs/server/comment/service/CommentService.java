package com.greenUs.server.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.comment.domain.Comment;
import com.greenUs.server.comment.dto.CommentRequestDto;
import com.greenUs.server.comment.dto.CommentResponseDto;
import com.greenUs.server.comment.repository.CommentRepository;
import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	// 댓글 작성
	@Transactional
	public Long setCommentWriting(CommentRequestDto commentRequestDto) {

		Post post = postRepository.findById(commentRequestDto.getPostId())
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		// 댓글 작성자 불러와서 dto에 넣어서 변환 후 저장하기(지금은 X)
		Comment comment = Comment.builder()
			.post(post)
			.content(commentRequestDto.getContent())
			.build();
		Comment commentResult = commentRepository.save(comment);

		return new CommentResponseDto(commentResult).getId();
	}

	// 댓글 수정
	@Transactional
	public Long setCommentModification(Long id, CommentRequestDto commentRequestDto) {

		// 입력받은 postId와 댓글id의 postID가 같은지 확인 (지금은 X)
		Post post = postRepository.findById(commentRequestDto.getPostId())
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		Comment comment = commentRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Comment is not Existing"));

		// 댓글 작성자 관련 처리 (지금은 X)
		comment.update(
			commentRequestDto.getContent()
		);

		return new CommentResponseDto(comment).getId();
	}
}
