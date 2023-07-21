package com.blue.bluearchive.admin.repository;

import com.blue.bluearchive.admin.dto.AdminSearchDto;
import com.blue.bluearchive.board.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminCommentRepositoryCustom {
    Page<Comment> getCommentPage(AdminSearchDto commentSearchDto, Pageable pageable);

    Page<Comment> getBoardCommentPage(int category, AdminSearchDto commentSearchDto, Pageable pageable);

    Page<Comment> getMemberPage(Long selectUser, Pageable pageable);
}
