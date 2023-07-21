package com.blue.bluearchive.board.repository;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.dto.BoardFormDto;
import com.blue.bluearchive.board.dto.BoardSearchDto;
import com.blue.bluearchive.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BoardRepositoryCustom {
    Page<Board> getBoardPage(BoardSearchDto boardSearchDto, Pageable pageable);

    Page<Board> getCategoryBoardPage(Category category, BoardSearchDto boardSearchDto, Pageable pageable);




}
