package com.blue.bluearchive.userpage.service;


import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.board.entity.QCommentsComment;
import com.blue.bluearchive.board.repository.CommentRepository;
import com.blue.bluearchive.board.repository.CommentsCommentRepository;
import com.blue.bluearchive.userpage.dto.SearchDto;
import com.blue.bluearchive.userpage.dto.UserPageCommentDto;
import com.blue.bluearchive.userpage.dto.UserPageCommentsCommentDto;
import com.blue.bluearchive.userpage.repository.UserCommentsCommentPageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPageCommentsCommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final CommentsCommentRepository commentsCommentRepository;
    private final UserCommentsCommentPageRepository userCommentsCommentPageRepository;

    private Page<UserPageCommentsCommentDto> changePageObject(Page<CommentsComment> comments){ //페이징 객체로 dto 넣어서 변환해주는 메서드
        List<UserPageCommentsCommentDto> commentDtos = new ArrayList<>();
        for (CommentsComment comment : comments.getContent()) {
            commentDtos.add(modelMapper.map(comment, UserPageCommentsCommentDto.class));
        }
        long total = comments.getTotalElements();
        return new PageImpl<>(commentDtos,comments.getPageable(),total);
    }

    private Page<UserPageCommentsCommentDto> changePageObject(List<CommentsComment> comments,Category category,Pageable pageable){ //페이징 객체로 dto 넣어서 변환해주는 메서드
        List<UserPageCommentsCommentDto> commentDtos = new ArrayList<>();
        for (CommentsComment comment : comments) {
            if(comment.getComment().getBoard().getCategory().getCategoryId()==category.getCategoryId())
                commentDtos.add(modelMapper.map(comment, UserPageCommentsCommentDto.class));
        }
        long total = Long.valueOf(commentDtos.size());
        // page start (offset)
        int start = (int) pageable.getOffset();
        // page end (toIndex)
        int end = Math.min((start + pageable.getPageSize()), commentDtos.size());
        // sublist from start to end
        List<UserPageCommentsCommentDto> page = commentDtos.subList(start, end);
        return new PageImpl<>(page,pageable,total);
    }

    @Transactional(readOnly = true)
    public Page<UserPageCommentsCommentDto> getCommentsCommentByCreatedBy(String userIdx, SearchDto boardSearchDto, Pageable pageable){
        Page<CommentsComment> comments = userCommentsCommentPageRepository.getCreatedByCommentsCommentPage(userIdx,boardSearchDto,pageable);
        return changePageObject(comments);
    }

    @Transactional(readOnly = true)
    public Page<UserPageCommentsCommentDto> getCommentsCommentByCreatedBy(Category category, String createdBy, SearchDto boardSearchDto, Pageable pageable){

        List<CommentsComment> comments;
        if (boardSearchDto.getSearchQuery().isEmpty()){
            comments = userCommentsCommentPageRepository.findByCreatedBy(createdBy);
        }else {
            if(StringUtils.equals("commentsCommentContent",boardSearchDto.getSearchBy())){
                comments = userCommentsCommentPageRepository.findByCreatedByAndCommentsCommentContentContaining(createdBy,boardSearchDto.getSearchQuery());
            }else if(StringUtils.equals("commentContent",boardSearchDto.getSearchBy())){
                comments = userCommentsCommentPageRepository.findByCreatedByAndCommentCommentContentContaining(createdBy,boardSearchDto.getSearchQuery());
            }else {
                comments = userCommentsCommentPageRepository.findByCreatedBy(createdBy);
            }
        }
        return changePageObject(comments,category,pageable);
    }


}
