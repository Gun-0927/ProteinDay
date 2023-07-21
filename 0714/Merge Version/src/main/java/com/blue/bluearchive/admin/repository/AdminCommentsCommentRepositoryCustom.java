package com.blue.bluearchive.admin.repository;

import com.blue.bluearchive.admin.dto.AdminSearchDto;
import com.blue.bluearchive.board.entity.CommentsComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminCommentsCommentRepositoryCustom {
    Page<CommentsComment> getCommentsCommentPage(AdminSearchDto commentSearchDto, Pageable pageable);

    Page<CommentsComment> getMemberCommentsComment(Long selectUser, Pageable pageable);
}
