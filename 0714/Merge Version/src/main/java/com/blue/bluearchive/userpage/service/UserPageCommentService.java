package com.blue.bluearchive.userpage.service;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.board.repository.CommentRepository;
import com.blue.bluearchive.board.repository.CommentsCommentRepository;
import com.blue.bluearchive.userpage.dto.SearchDto;
import com.blue.bluearchive.userpage.dto.UserPageCommentDto;
import com.blue.bluearchive.userpage.repository.UserCommentPageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPageCommentService {
    private final ModelMapper modelMapper;
    private final CommentsCommentRepository commentsCommentRepository;

    private final UserCommentPageRepository userCommentRepository;


    private Page<UserPageCommentDto> changePageObject(Page<Comment> comments){ //페이징 객체로 dto 넣어서 변환해주는 메서드
        List<UserPageCommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments.getContent()) {
            commentDtos.add(modelMapper.map(comment, UserPageCommentDto.class));
            //대댓글 개수 가져오기
            List<CommentsComment> commentsComments = commentsCommentRepository.findByComment(comment);
            commentDtos.get(commentDtos.size()-1).setCommentsCommentCount(commentsComments.size());
        }
        long total = comments.getTotalElements();
        return new PageImpl<>(commentDtos,comments.getPageable(),total);
    }

    @Transactional(readOnly = true)
    public Page<UserPageCommentDto> getCommentByCreatedBy(String userIdx, SearchDto boardSearchDto, Pageable pageable){
        Page<Comment> comments = userCommentRepository.getCreatedByCommentPage(userIdx,boardSearchDto,pageable);
        return changePageObject(comments);
    }

    @Transactional(readOnly = true)
    public Page<UserPageCommentDto> getCommentByCreatedBy(Category category, String userIdx, SearchDto boardSearchDto, Pageable pageable){
        Page<Comment> comments = userCommentRepository.getCreatedByCommentPage(category,userIdx,boardSearchDto,pageable);
        return changePageObject(comments);
    }

}
