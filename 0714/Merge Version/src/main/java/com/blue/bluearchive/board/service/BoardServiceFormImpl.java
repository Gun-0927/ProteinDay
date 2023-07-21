package com.blue.bluearchive.board.service;

import com.blue.bluearchive.board.dto.BoardDto;
import com.blue.bluearchive.board.repository.BoardRepositoryForm;

public class BoardServiceFormImpl implements BoardServiceForm{

    private BoardRepositoryForm boardRepo;
    @Override
    public void createBoard(BoardDto boardDto) {
        boardRepo.createBoard(boardDto);
    }
}
