package com.greenUs.server.attachment.domain;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.post.domain.Post;

import javax.persistence.*;

@Entity
public class Attachment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
