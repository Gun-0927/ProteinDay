package com.blue.bluearchive.board.dto;

import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.BoardImg;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class BoardImgDto {
    private int boardImgId;
    private String boardImgUrl;
    private Board board;
    private String imgName;
    private String oriImgName;
    private static ModelMapper modelMapper = new ModelMapper();

    public static BoardImgDto of(BoardImg boardImg){
        return  modelMapper.map(boardImg, BoardImgDto.class);
    }

}
