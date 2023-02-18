package com.greenUs.server.member.dto.response;

import com.greenUs.server.bookmark.domain.Bookmark;

public class MyPageContentResponse {
    private Long id;
    private String title;
    private String content;

    public MyPageContentResponse(Bookmark bookmark) {
        this.id = bookmark.getId();
        this.title = bookmark.getPost().getTitle();
        this.content = bookmark.getPost().getContent();
    }
}
