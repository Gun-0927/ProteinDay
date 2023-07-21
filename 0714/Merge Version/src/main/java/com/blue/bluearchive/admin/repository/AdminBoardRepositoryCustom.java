package com.blue.bluearchive.admin.repository;

import com.blue.bluearchive.admin.dto.AdminSearchDto;
import com.blue.bluearchive.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminBoardRepositoryCustom {
    Page<Board> getBoardPage(AdminSearchDto commentSearchDto, Pageable pageable);

    Page<Board> getCategoryBoardPage(int category, AdminSearchDto boardSearchDto, Pageable pageable);


    Page<Board> getMemberPage(Long selectUser, Pageable pageable);

}
