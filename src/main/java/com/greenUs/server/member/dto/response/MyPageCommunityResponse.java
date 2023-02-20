package com.greenUs.server.member.dto.response;

import com.greenUs.server.comment.dto.CommentResponseDto;
import com.greenUs.server.post.dto.PostResponseDto;
import org.springframework.data.domain.Page;

public class MyPageCommunityResponse {

    private Page<PostResponseDto> postResponses;
    private Page<CommentResponseDto> commentResponses;
}