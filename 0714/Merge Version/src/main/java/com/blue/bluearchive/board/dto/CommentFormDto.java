package com.blue.bluearchive.board.dto;


import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;


//건희 추가
@Data
public class CommentFormDto {
    @NotBlank(message = "내용은 필수 입니다")
    private String commentContent;
    private Board boardId;

    private static ModelMapper modelMapper = new ModelMapper();
    public Comment createBoard(){
        Comment comment = modelMapper.map(this, Comment.class);
        comment.setCommentId(0);
        return comment;
    }

    public static CommentFormDto of(Comment comment){
        return modelMapper.map(comment, CommentFormDto.class);
    }

}