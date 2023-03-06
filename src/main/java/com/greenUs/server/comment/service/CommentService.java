package com.greenUs.server.comment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.comment.domain.Comment;
import com.greenUs.server.comment.dto.CommentRequest;
import com.greenUs.server.comment.dto.CommentResponse;
import com.greenUs.server.comment.repository.CommentRepository;
import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private static final int PAGE_COMMENT_COUNT = 100;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	public Page<CommentResponse> getCommentLists(Long postId, Integer page) {

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		PageRequest pageRequest = PageRequest.of(page, PAGE_COMMENT_COUNT, Sort.by(Sort.Direction.ASC, "createdAt"));

		Page<Comment> comment = commentRepository.findByPostId(postId, pageRequest);

		Page<CommentResponse> commentResponseDto = comment.map(CommentResponse::new);

		return commentResponseDto;
	}

	@Transactional
	public void createComment(CommentRequest commentRequestDto) {

		Post post = postRepository.findById(commentRequestDto.getPostId())
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		// 댓글 작성자 저장 생략

		Comment comment = Comment.builder()
			.post(post)
			.content(commentRequestDto.getContent())
			.build();

		commentRepository.save(comment);
	}

	@Transactional
	public void createRecomment(Long parentId, CommentRequest commentRequestDto) {

		Post post = postRepository.findById(commentRequestDto.getPostId())
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		// 댓글 작성자 확인 생략

		Comment comment = Comment.builder()
			.post(post)
			.content(commentRequestDto.getContent())
			.build();

		comment.confirmParent(commentRepository.findById(parentId)
			.orElseThrow(() -> new IllegalArgumentException("ParentComment is not Existing")));

		commentRepository.save(comment);
	}

	@Transactional
	public void updateComment(Long id, CommentRequest commentRequestDto) {

		Comment comment = commentRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Comment is not Existing"));

		// 댓글 작성자 확인 생략

		comment.update(
			commentRequestDto.getContent()
		);
	}

	@Transactional
	public void deleteComment(Long id) {

		Comment comment = commentRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Comment is not Existing"));

		// 댓글 작성자 확인 생략
		comment.remove();
		List<Comment> removableCommentList = comment.findRemovableList();
		commentRepository.deleteAll(removableCommentList);
	}
}
