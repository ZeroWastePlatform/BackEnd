package com.greenUs.server.member.dto.response;

import com.greenUs.server.comment.dto.CommentResponse;
import com.greenUs.server.post.dto.PostResponse;
import lombok.Getter;
import org.springframework.data.domain.Page;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MyPageCommunityResponse {

    private MyPageProfileResponse myPageProfileResponse;
    private Page<PostResponse> postResponses;
    private Page<CommentResponse> commentResponses;

    public MyPageCommunityResponse(MyPageProfileResponse myPageProfileResponse) {
        this.myPageProfileResponse = myPageProfileResponse;
    }
}
