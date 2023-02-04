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

	@Transactional
	public Long setCommentWriting(CommentRequestDto commentRequestDto) {

		Post post = postRepository.findById(commentRequestDto.getPostId()).orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패 : 해당 게시글이 존재하지 않습니다."));

		// 댓글 작성자 불러와서 dto에 넣어서 변환 후 저장하기(지금은 X)
		Comment comment = Comment.builder()
			.post(post)
			.content(commentRequestDto.getContent())
			.build();
		Comment commentResult = commentRepository.save(comment);
		System.out.println("commentResult = " + commentResult);
		return new CommentResponseDto(commentResult).getId();
	}
}
