package com.blue.bluearchive.userpage.repository;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.userpage.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCommentsCommentPageRepositoryCustom {
    Page<CommentsComment> getCreatedByCommentsCommentPage(String createdBy, SearchDto searchDto, Pageable pageable);

//    Page<CommentsComment> getCreatedByCommentsCommentPage(Category category, String createdBy, SearchDto searchDto, Pageable pageable);
}
