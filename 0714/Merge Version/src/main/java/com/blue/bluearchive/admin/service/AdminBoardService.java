package com.blue.bluearchive.admin.service;

import com.blue.bluearchive.admin.dto.AdminBoardDto;
import com.blue.bluearchive.admin.dto.AdminSearchDto;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.admin.repository.AdminBoardRepository;
import com.blue.bluearchive.admin.repository.CategoryRepository;
import com.blue.bluearchive.board.dto.BoardDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.repository.BoardRepository;
import com.blue.bluearchive.board.repository.CommentRepository;
import com.blue.bluearchive.report.repository.ReportBoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdminBoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final AdminBoardRepository adminBoardRepository;
    private final ReportBoardRepository reportBoardRepository;

    private Page<AdminBoardDto> getComment(Page<Board> boards) {
        List<AdminBoardDto> boardDtos = new ArrayList<>();
        for (Board board : boards.getContent()) {
            boardDtos.add(modelMapper.map(board, AdminBoardDto.class));
            int size = reportBoardRepository.findByBoardBoardIdAndReportStatusFalse(board.getBoardId()).size();
            boardDtos.get(boardDtos.size()-1).setNotReportCount(size);
        }
        long total = boards.getTotalElements();
        return new PageImpl<>(boardDtos, boards.getPageable(), total);
    }

    @Transactional(readOnly = true)
    public Page<AdminBoardDto> getBoardByCreatedBy(int categoryid, AdminSearchDto searchDto, Pageable pageable){
        Page<AdminBoardDto> adminCommentDtoPage = null;
        Page<Board> boardCommentPage =null;
        if(categoryid==0){
            boardCommentPage = adminBoardRepository.getBoardPage(searchDto, pageable);
            adminCommentDtoPage = getComment(boardCommentPage);
        }else{
            boardCommentPage = adminBoardRepository.getCategoryBoardPage(categoryid, searchDto, pageable);
            adminCommentDtoPage = getComment(boardCommentPage);
        }
        return adminCommentDtoPage;
    }


    // 승훈 보드 추가 내역

    public List<BoardDto> getBoardsByCategoryId(int categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));
        List<Board> boardEntities = boardRepository.findByCategory(category);
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardEntities) {
            boardDtos.add(modelMapper.map(board, BoardDto.class));
        }
        return boardDtos;
    }
    public void delete(int boardId) {
        // 게시물 삭제
        boardRepository.deleteById(boardId);
    }

    public void deleteBoards(List<Integer> boardIds) {
        for (Integer boardId : boardIds) {
            Board board = boardRepository.findById(boardId).orElseThrow(NoSuchElementException::new);

            List<Comment> comments = commentRepository.findByBoard(board);
            for (Comment comment : comments) {
                commentRepository.delete(comment);
            }
            // 댓글 삭제
            commentRepository.deleteAll(comments);

            // 게시글 삭제
            boardRepository.delete(board);
        }
    }
    //게시글 수정 부분 코드추가
    @Transactional
    public void updateCategoryNameById(int boardId, int categoryId) {
        adminBoardRepository.updateCategoryNameById(boardId, categoryId);
    }
}
