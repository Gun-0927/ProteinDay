package com.blue.bluearchive.board.repository;

import com.blue.bluearchive.board.dto.BoardDto;
import com.blue.bluearchive.board.dto.BoardFormDto;

public interface BoardRepositoryForm {
    void createBoard(BoardDto boardDto);
}
