package com.blue.bluearchive.board.service;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.dto.BoardDto;
import com.blue.bluearchive.board.dto.BoardFormDto;
import com.blue.bluearchive.board.dto.BoardImgDto;
import com.blue.bluearchive.board.dto.BoardSearchDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.BoardImg;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.board.repository.BoardRepository;
import com.blue.bluearchive.board.repository.CommentRepository;
import com.blue.bluearchive.board.repository.CommentsCommentRepository;
import com.blue.bluearchive.board.repository.formRepository.BoardImgRepository;
import com.blue.bluearchive.userpage.service.UserCategoryLogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardImgRepository boardImgRepository;
    private final BoardImgService boardImgService;
    private final ModelMapper modelMapper;
    private final UserCategoryLogService userCategoryLogService;

    private final CommentRepository commentRepository;
    private final CommentsCommentRepository commentsCommentRepository;


    public List<BoardDto> getAllBoards() {
        List<Board> boardEntities = boardRepository.findAll();
        List<BoardDto> BoardDtos = new ArrayList<>();
        for (Board board : boardEntities) {
            BoardDtos.add(modelMapper.map(board, BoardDto.class));
        }
        return BoardDtos;
    }

    public List<BoardDto> getBoardsByCategory(Category category) {
        List<Board> boardEntities = boardRepository.findByCategory(category);
        List<BoardDto> boardDtos = new ArrayList<>();
        for (Board board : boardEntities) {
            boardDtos.add(modelMapper.map(board, BoardDto.class));
        }
        return boardDtos;
    }
    public BoardDto getBoardById(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));
        userCategoryLogService.categoryCount(board.getCategory());
        return modelMapper.map(board, BoardDto.class);
    }
    public BoardFormDto getBoardImgById(int boardId){
        List<BoardImg> boardImgList=boardImgRepository.findByBoardBoardIdOrderByBoardImgIdAsc(boardId);
        List<BoardImgDto> boardImgDtoList = new ArrayList<>();
        for(BoardImg boardImg : boardImgList){
            BoardImgDto boardImgDto = BoardImgDto.of(boardImg);
            boardImgDtoList.add(boardImgDto);
        }
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));
        BoardFormDto boardFormDto = BoardFormDto.of(board);
        boardFormDto.setBoardImgDtoList(boardImgDtoList);
        return boardFormDto;
    }

        public void updateCommentCount(Board boardDto) {
            // 엔티티를 업데이트하는 로직을 추가합니다.
            Board board = boardRepository.findById(boardDto.getBoardId()).orElse(null);
            if (board != null) {
                // 엔티티의 필드를 업데이트합니다.
                board.setCommentCount(boardDto.getCommentCount());
                // Repository를 통해 엔티티를 저장 또는 업데이트합니다.
                boardRepository.save(board);
            }
        }
        public Integer updateBoard(BoardFormDto boardFormDto,List<String>boardImgUrlList,List<MultipartFile> boardImgFileList)throws Exception{

        Board board = boardRepository.findById(boardFormDto.getBoardId()).orElseThrow(EntityNotFoundException::new);
             board.updateBoard(boardFormDto);
             boardRepository.save(board);
             List<BoardImg> boardImg = boardImgRepository.findByBoardBoardIdOrderByBoardImgIdAsc(boardFormDto.getBoardId());
            List<String>boardImgUrl = new ArrayList<>();
            for(BoardImg imgUrl:boardImg){
                boardImgUrl.add(imgUrl.getBoardImgUrl());
            }

            List<String> imagesToDelete = new ArrayList<>();
            for (String imgUrl : boardImgUrl) {
                if (!boardImgUrlList.contains(imgUrl)) {
                    imagesToDelete.add(imgUrl);
                    System.out.println(imgUrl);
                    System.out.println("여깅");
                }
            }


            for (String imageUrl : imagesToDelete) {
                BoardImg deletedImg = boardImgRepository.findByBoardImgUrl(imageUrl);

                if (deletedImg != null) {
                    System.out.println(deletedImg.getBoardImgId());
                    // 필요한 작업 수행
                    boardImgRepository.delete(deletedImg);
                }

            }



            if (boardImgFileList != null) {
               saveBoardImages(board,boardImgFileList);
            }


        return board.getBoardId();
        }

    public void incrementBoardCount(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));
        if (board != null) {
            board.setBoardCount(board.getBoardCount() + 1);
            boardRepository.save(board);
        }
    }
    public void incrementBoardLikeCount(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));
        if (board != null) {
            board.setBoardLikeCount(board.getBoardLikeCount() + 1);
            boardRepository.save(board);
        }
    }
    public void incrementBoardHateCount(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));
        if (board != null) {
            board.setBoardHateCount(board.getBoardHateCount() + 1);
            boardRepository.save(board);
        }
    }
    public void decreaseBoardLikeCount(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));
        if (board != null) {
            board.setBoardLikeCount(board.getBoardLikeCount() - 1);
            boardRepository.save(board);
        }
    }
    public void decreaseBoardHateCount(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found"));
        if (board != null) {
            board.setBoardHateCount(board.getBoardHateCount() - 1);
            boardRepository.save(board);
        }
    }


    public Integer saveBoard(BoardFormDto boardFormDto, List<MultipartFile> boardImgFileList) throws Exception {
        Board board = boardFormDto.createBoard();
        boardRepository.save(board);

        if (boardImgFileList != null) {
            System.out.println("==============보드저장 직후 if진입===============");
            saveBoardImages(board, boardImgFileList);
        }

        return board.getBoardId();
    }
    public void saveBoardImages(Board board, List<MultipartFile> boardImgFileList) throws Exception {
        System.out.println("============saveBoardImages 메서드 진입============");
        for (int i = 0; i < boardImgFileList.size(); i++) {
            System.out.println("============saveBoardImages 메서드 포문 진입============");
            BoardImg boardImg = new BoardImg();
            boardImg.setBoard(board);
            
            boardImgService.saveBoardImg(boardImg, boardImgFileList.get(i));
        }
            
    }
    public int getBoardLikeCount(int boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        return (board != null) ? board.getBoardLikeCount() : 0;
    }
    public int getBoardHateCount(int boardId) {
        Board board = boardRepository.findById(boardId).orElse(null);
        return (board != null) ? board.getBoardHateCount() : 0;
    }

    @Transactional(readOnly = true)
    public Page<Board> getBoardPage(BoardSearchDto boardSearchDto, Pageable pageable){
        return boardRepository.getBoardPage(boardSearchDto,pageable);
    }

    @Transactional(readOnly = true)
    public Page<Board> getCategoryBoardPage(Category category,BoardSearchDto boardSearchDto, Pageable pageable){
        return boardRepository.getCategoryBoardPage(category,boardSearchDto,pageable);
    }

    @Transactional(readOnly = true)
    public Page<Board> getBoardsWithLikesGreaterThanEqual(int likeCount, Pageable pageable) {
        return boardRepository.findByBoardLikeCountGreaterThanEqual(likeCount, pageable);
    }

}


