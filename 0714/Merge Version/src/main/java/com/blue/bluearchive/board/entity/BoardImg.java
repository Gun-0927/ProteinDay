package com.blue.bluearchive.board.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "board_img")
@Data
public class BoardImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_img_id")
    private int boardImgId;

    @Column(name = "board_img_url", length = 100)
    private String boardImgUrl;
    private String imgName; //이미지 파일명
    private String oriImgName; //원본 이미지 파일명



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void updateBoardImg(String oriImgName,String imgName,String boardImgUrl){
        this.oriImgName=oriImgName;
        this.imgName=imgName;
        this.boardImgUrl=boardImgUrl;
    }


}
