package com.blue.bluearchive.board.entity;

import com.blue.bluearchive.member.entity.Member;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "comments_comment_Like_Hate")
public class CommentsCommentLikeHate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Like_Hate_id")
    private Integer likeHateId;

    @Column(name = "comments_comment_Like")
    private boolean like;

    @Column(name = "comments_comment_Hate")
    private boolean hate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comments_comment_id")
    private CommentsComment commentsComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;
}
