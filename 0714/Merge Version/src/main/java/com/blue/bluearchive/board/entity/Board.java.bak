package com.blue.bluearchive.board.entity;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.dto.BoardFormDto;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.report.entity.Report;
import com.blue.bluearchive.shop.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "board")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private int boardId;

    @Lob
    @Column(name = "board_content", nullable = false)
    private String boardContent;

    @Column(name = "board_count")
    private Integer boardCount = 0;

    @Column(name = "board_hate_count")
    private Integer boardHateCount = 0;

    @Column(name = "board_like_count")
    private Integer boardLikeCount = 0;

    @Column(name = "board_pre_count")
    private Integer boardPreCount = 0;

    @Column(name = "board_reports_count")
    private Integer boardReportsCount = 0;

    @Column(name = "board_title", nullable = false)
    private String boardTitle;

    @Column(name="board_comment_Count")
    private Integer commentCount=0;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_idx")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "category_id")
    private Category category;

    public void updateBoard(BoardFormDto boardFormDto){
        this.boardTitle=boardFormDto.getBoardTitle();
        this.boardContent=boardFormDto.getBoardContent();
        this.category=boardFormDto.getCategory();
    }
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardImg> boardImg;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardLikeHate> boardLikeHate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comment;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Report> report;


}
