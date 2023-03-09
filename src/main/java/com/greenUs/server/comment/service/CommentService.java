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
import com.greenUs.server.comment.exception.NotEqualMemberAndCommentMember;
import com.greenUs.server.comment.exception.NotFoundCommentException;
import com.greenUs.server.comment.repository.CommentRepository;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.post.domain.Post;
import com.greenUs.server.post.exception.NotFoundPostException;
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

		postRepository.findById(postId).orElseThrow(NotFoundPostException::new);

		PageRequest pageRequest = PageRequest.of(page, PAGE_COMMENT_COUNT, Sort.by(Sort.Direction.ASC, "createdAt"));

		Page<Comment> comment = commentRepository.findByPostId(postId, pageRequest);

		Page<CommentResponse> commentResponseDto = comment.map(CommentResponse::new);

		return commentResponseDto;
	}

	@Transactional
	public void createComment(CommentRequest commentRequestDto, Member member) {

		Post post = postRepository.findById(commentRequestDto.getPostId())
			.orElseThrow(NotFoundPostException::new);

		Comment comment = Comment.builder()
			.member(member)
			.post(post)
			.content(commentRequestDto.getContent())
			.build();

		commentRepository.save(comment);
	}

	@Transactional
	public void createRecomment(Long parentId, CommentRequest commentRequestDto, Member member) {

		Post post = postRepository.findById(commentRequestDto.getPostId())
			.orElseThrow(NotFoundPostException::new);

		Comment comment = Comment.builder()
			.member(member)
			.post(post)
			.content(commentRequestDto.getContent())
			.build();

		comment.confirmParent(commentRepository.findById(parentId)
			.orElseThrow(() -> new IllegalArgumentException("ParentComment is not Existing")));

		commentRepository.save(comment);
	}

	@Transactional
	public void updateComment(Long id, CommentRequest commentRequestDto, Member member) {

		Comment comment = commentRepository.findById(id)
			.orElseThrow(NotFoundCommentException::new);

		if (!member.getId().equals(comment.getMember().getId())) {
			throw new NotEqualMemberAndCommentMember();
		}

		comment.update(
			commentRequestDto.getContent()
		);
	}

	@Transactional
	public void deleteComment(Long id, Member member) {

		Comment comment = commentRepository.findById(id)
			.orElseThrow(NotFoundCommentException::new);

		if (!member.getId().equals(comment.getMember().getId())) {
			throw new NotEqualMemberAndCommentMember();
		}

		comment.remove();
		List<Comment> removableCommentList = comment.findRemovableList();
		commentRepository.deleteAll(removableCommentList);
	}
}
