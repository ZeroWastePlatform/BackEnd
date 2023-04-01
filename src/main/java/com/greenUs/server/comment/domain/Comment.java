package com.greenUs.server.comment.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.greenUs.server.common.BaseEntity;
import com.greenUs.server.member.domain.Member;
import com.greenUs.server.post.domain.Post;

import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(length = 1000, nullable = false)
    private String content;

    private boolean isRemoved= false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> childList = new ArrayList<>();

    // public void confirmWriter(Member writer) {
    //     this.writer = writer;
    //     writer.addComment(this);
    // }
    //
    // public void confirmPost(Post post) {
    //     this.post = post;
    //     post.addComment(this);
    // }

    public void confirmParent(Comment parent){
        this.parent = parent;
        parent.addChild(this);
    }

    public void addChild(Comment child){
        childList.add(child);
    }

    @Builder
    protected Comment(Member member, Post post, Comment parent, String content) {
        this.member = member;
        this.post = post;
        this.parent = parent;
        this.content = content;
        this.isRemoved = false;
    }

    public void update (String content) {
        this.content = content;
    }

    public void remove() {
        this.isRemoved = true;
    }

    public List<Comment> findRemovableList() {

        List<Comment> result = new ArrayList<>();

        Optional.ofNullable(this.parent).ifPresentOrElse(

            parentComment -> { // 대댓글인 경우(부모가 존재하는 경우)
                if( parentComment.isRemoved() && parentComment.isAllChildRemoved()){
                    result.addAll(parentComment.getChildList());
                    result.add(parentComment);
                }
            },

            () -> { // 댓글인 경우
                if (isAllChildRemoved()) {
                    result.add(this);
                    result.addAll(this.getChildList());
                }
            }
        );

        return result;
    }

    private boolean isAllChildRemoved() {
        return getChildList().stream()
            .map(Comment::isRemoved)
            .filter(isRemove -> !isRemove)
            .findAny()
            .orElse(true);
    }
}
