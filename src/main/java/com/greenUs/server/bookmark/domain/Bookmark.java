package com.greenUs.server.bookmark.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.post.domain.Post;

import javax.persistence.*;

@Entity
public class Bookmark extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
