package com.greenUs.server.member.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MyPageCommunityResponse {

    private MyPageProfileResponse myPageProfileResponse;
    private Page<MyPagePostResponse> postResponses;
    private Page<MyPageCommentResponse> commentResponses;

    public MyPageCommunityResponse(MyPageProfileResponse myPageProfileResponse) {
        this.myPageProfileResponse = myPageProfileResponse;
    }
}
