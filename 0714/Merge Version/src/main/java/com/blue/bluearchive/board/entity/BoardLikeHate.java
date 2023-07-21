    package com.blue.bluearchive.board.entity;

    import com.blue.bluearchive.member.entity.Member;
    import lombok.Data;

    import javax.persistence.*;

    @Entity
    @Data
    @Table(name = "board_like_hate")
    public class BoardLikeHate {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "Like_Hate_id")
        private Integer likeHateId;

        @Column(name = "board_Like")
        private boolean like;

        @Column(name = "board_Hate")
        private boolean hate;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "board_id")
        private Board board;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_idx")
        private Member member;


    }
