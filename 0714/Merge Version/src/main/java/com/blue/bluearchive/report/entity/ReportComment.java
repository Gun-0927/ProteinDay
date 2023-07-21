package com.blue.bluearchive.report.entity;//package com.blue.bluearchive.report.entity;
//
//import com.blue.bluearchive.board.entity.Board;
//import com.blue.bluearchive.board.entity.Comment;
//import com.blue.bluearchive.board.entity.CommentsComment;
//import com.blue.bluearchive.shop.entity.BaseEntity;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Data
//@Table(name = "report_comment")
//@Setter
//@Getter
//public class ReportComment extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "report_comment_id")
//    private int reportCommentId;
//
//    @ManyToOne
//    @JoinColumn(name = "board_id")
//    private Board boardId;
//
//    @ManyToOne
//    @JoinColumn(name = "comment_id")
//    private Comment commentId;
//
//    @ManyToOne
//    @JoinColumn(name = "comments_comment_id")
//    private CommentsComment commentsCommentId;
//
//    @Column(name = "comment_created_by")
//    private String commentCreatedBy;
//
//    @Column(name = "report_comment_content", length = 300, nullable = false)
//    private String reportCommentContent;
//
//    @Column(name = "report_comment_category", length = 30, nullable = false)
//    private String reportCommentCategory;
//}
