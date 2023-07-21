package com.blue.bluearchive.board.entity;

import com.blue.bluearchive.member.entity.Member;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "comment_Like_Hate")
public class CommentLikeHate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Like_Hate_id")
    private Integer likeHateId;

    @Column(name = "comment_Like")
    private boolean like;

    @Column(name = "comment_Hate")
    private boolean hate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;
}
