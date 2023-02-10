package com.greenUs.server.comment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenUs.server.comment.domain.Comment;
import com.greenUs.server.comment.dto.CommentRequestDto;
import com.greenUs.server.comment.dto.CommentResponseDto;
import com.greenUs.server.comment.repository.CommentRepository;
import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.dto.PostResponseDto;
import com.greenUs.server.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private static final int PAGE_COMMENT_COUNT = 100; // 한 화면에 보일 댓글 수
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	// 댓글 조회
	public Page<CommentResponseDto> getCommentDetail(Long postId, Integer page) {

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		PageRequest pageRequest = PageRequest.of(page, PAGE_COMMENT_COUNT, Sort.by(Sort.Direction.ASC, "createdAt"));

		Page<Comment> comment = commentRepository.findByPostId(postId, pageRequest);

		Page<CommentResponseDto> commentResponseDto = comment.map(CommentResponseDto::new);

		return commentResponseDto;
	}

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

	// 대댓글 작성
	@Transactional
	public Long setReCommentWriting(Long parentId, CommentRequestDto commentRequestDto) {

		Comment comment = commentRequestDto.toEntity();

		// 게시글 확인
		Post post = postRepository.findById(commentRequestDto.getPostId())
			.orElseThrow(() -> new IllegalArgumentException("Post is not Existing"));

		// 댓글 작성자 확인 생략

		// 부모 댓글 확인
		comment.confirmParent(commentRepository.findById(parentId)
			.orElseThrow(() -> new IllegalArgumentException("ParentComment is not Existing")));

		commentRepository.save(comment);

		return new CommentResponseDto(comment).getId();
	}

	// 댓글 수정
	@Transactional
	public Long setCommentModification(Long id, CommentRequestDto commentRequestDto) {

		// 입력받은 postId와 댓글id의 postID가 같은지 확인 (지금은 X) - 필요없는 부분(클라이언트 요청 문제)
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

	// 댓글 삭제
	@Transactional
	public Long setCommentDeletion(Long id) {

		Comment comment = commentRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Comment is not Existing"));

		CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

		// 댓글 작성자 관련 처리 (지금은 X)
		commentRepository.delete(comment);

		return commentResponseDto.getId();
	}
}
