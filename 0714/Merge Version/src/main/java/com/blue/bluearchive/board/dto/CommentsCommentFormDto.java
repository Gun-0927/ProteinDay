package com.blue.bluearchive.board.dto;


import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;


//건희 추가
@Data
public class CommentsCommentFormDto {
    @NotBlank(message = "내용은 필수 입니다")
    private String commentsCommentContent;
    private Comment comment;

    private static ModelMapper modelMapper = new ModelMapper();
    public CommentsComment createBoard() {
        CommentsComment commentsComment = modelMapper.map(this, CommentsComment.class);
        commentsComment.setCommentsCommentId(0); // commentsCommentId를 0으로 설정하여 새로운 엔티티로 인식
        //return modelMapper.map(this,CommentsComment.class) ;
        commentsComment.setCommentsCommentLikeCount(0);
        commentsComment.setCommentsCommentHateCount(0);
        commentsComment.setCommentsCommentReportsCount(0);
        return commentsComment;
    }
    public static CommentsCommentFormDto of(CommentsComment commentsComment){
        return modelMapper.map(commentsComment, CommentsCommentFormDto.class);
    }

}
