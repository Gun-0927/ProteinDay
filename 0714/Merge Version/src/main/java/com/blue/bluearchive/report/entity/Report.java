package com.blue.bluearchive.report.entity;


import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.shop.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Data
@Table(name = "report")
public class Report extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private int reportId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "comments_comment_id")
    private CommentsComment commentsComment;

    @Column(name = "target_created_by")
    private String targetCreatedBy;

    @Column(name = "report_content", length = 300, nullable = false)
    private String reportContent;

    @Column(name = "report_category", length = 30, nullable = false)
    private String reportCategory;

    //승훈 추가
    @Column(name = "report_status",nullable = false)
    private boolean reportStatus;
}
