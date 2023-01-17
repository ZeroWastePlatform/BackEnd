package com.greenUs.server.post.domain;

import java.util.ArrayList;
import java.util.List;

import com.greenUs.server.attachment.domain.Attachment;
import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.hashtag.domain.Hashtag;
import com.greenUs.server.member.domain.Member;

import javax.persistence.*;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@DynamicUpdate
@DynamicInsert
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    // @OnDelete(action = OnDeleteAction.CASCADE) // 작성자 계정 탈퇴시 게시글도 삭제
    private Member member;

    @Column(nullable = false)
    private Integer kind;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private Integer price;

    @ColumnDefault("0")
    private Integer viewCnt;

    private Integer replyCnt;

    @ColumnDefault("0")
    private Integer recommendCnt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Hashtag> hashtags = new ArrayList<>();

    @ColumnDefault("0")
    private Integer fileAttached;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Attachment> attachments = new ArrayList<>();

    @Builder
    public Post(Integer kind, String title, String content, Integer price, Integer fileAttached) {
        this.kind = kind;
        this.title = title;
        this.content = content;
        this.price = price;
        this.fileAttached = fileAttached;
    }

    // 글 수정
    public void update (Integer kind, String title, String content, Integer price) {
        this.kind = kind;
        this.title = title;
        this.content = content;
        this.price = price;
    }
}
