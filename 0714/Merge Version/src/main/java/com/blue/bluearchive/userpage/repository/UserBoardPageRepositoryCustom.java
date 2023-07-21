package com.blue.bluearchive.userpage.repository;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.userpage.dto.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserBoardPageRepositoryCustom {
    //건희추가 유저페이지 사용
    Page<Board> getCreatedByBoardPage(String createdBy, SearchDto searchDto, Pageable pageable);
    Page<Board> getCreatedByBoardPage(Category category, String userIdx, SearchDto searchDto, Pageable pageable);

}
