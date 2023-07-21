package com.blue.bluearchive.board.entity;

import com.blue.bluearchive.report.entity.Report;
import com.blue.bluearchive.shop.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "comment")
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "comment_content", length = 300, nullable = false)
    private String commentContent;

    @Column(name = "comment_likeCount")
    private Integer commentLikeCount = 0;

    @Column(name = "comment_hateCount")
    private Integer commentHateCount = 0;

    @Column(name = "comment_reportsCount")
    private Integer commentReportsCount = 0;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLikeHate> commentLikeHate;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Report> report;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentsComment> commentsComment;




}