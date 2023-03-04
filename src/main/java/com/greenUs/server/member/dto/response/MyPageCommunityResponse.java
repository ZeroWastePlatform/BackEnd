package com.greenUs.server.member.dto.response;

import com.greenUs.server.comment.dto.CommentResponseDto;
import com.greenUs.server.post.dto.PostResponseDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MyPageCommunityResponse {

    private Page<PostResponseDto> postResponses;
    private Page<CommentResponseDto> commentResponses;
}
