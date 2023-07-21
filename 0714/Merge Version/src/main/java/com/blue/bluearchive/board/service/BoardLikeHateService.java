package com.blue.bluearchive.board.service;

import com.blue.bluearchive.board.dto.BoardDto;
import com.blue.bluearchive.board.dto.BoardLikeHateDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.BoardLikeHate;
import com.blue.bluearchive.board.repository.BoardLikeHateRepository;
import com.blue.bluearchive.board.repository.BoardRepository;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardLikeHateService {
    private final BoardLikeHateRepository boardLikeHateRepository;
    private  final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardService boardService;

    public BoardLikeHateDto likeBoard(int boardId, Long idx) {
        BoardLikeHate likeHate = boardLikeHateRepository.findByBoardBoardIdAndMemberIdx(boardId, idx);
        Board board = boardRepository.findById(boardId).orElse(null);
        Member member = memberRepository.findByIdx(idx);

        if (likeHate != null) {
            // 이미 좋아요를 눌렀을 경우, 좋아요 취소
            if (likeHate.isLike()) {
                likeHate.setLike(false);
                boardService.decreaseBoardLikeCount(boardId);
                boardLikeHateRepository.save(likeHate);
            } else {
                if (likeHate.isHate()) {
                    likeHate.setLike(false);
                    boardLikeHateRepository.save(likeHate);
                } else {
                    likeHate.setLike(true);
                    boardService.incrementBoardLikeCount(boardId);
                    boardLikeHateRepository.save(likeHate);
                }
            }
        }else {
            likeHate = new BoardLikeHate();
            likeHate.setBoard(board);
            likeHate.setMember(member);
            likeHate.setLike(true);
            boardService.incrementBoardLikeCount(boardId);
            boardLikeHateRepository.save(likeHate);
        }
        int likeCount = boardService.getBoardLikeCount(boardId);
        int hateCount = boardService.getBoardHateCount(boardId);

        return new BoardLikeHateDto(likeCount, hateCount,likeHate.isLike(),likeHate.isHate());
    }

    public BoardLikeHateDto hateBoard(int boardId, Long idx) {
        BoardLikeHate likeHate = boardLikeHateRepository.findByBoardBoardIdAndMemberIdx(boardId, idx);
        Board board = boardRepository.findById(boardId).orElse(null);
        Member member = memberRepository.findByIdx(idx);

        if (likeHate != null) {
            if (likeHate.isHate()) {
                likeHate.setHate(false);
                boardService.decreaseBoardHateCount(boardId);
                boardLikeHateRepository.save(likeHate);
            } else {
                if (likeHate.isLike()) {
                    likeHate.setHate(false);
                    boardLikeHateRepository.save(likeHate);
                } else {
                    likeHate.setHate(true);
                    boardService.incrementBoardHateCount(boardId);
                    boardLikeHateRepository.save(likeHate);
                }
            }
        }else {
            likeHate = new BoardLikeHate();
            likeHate.setBoard(board);
            likeHate.setMember(member);
            likeHate.setHate(true);
            boardService.incrementBoardHateCount(boardId);
            boardLikeHateRepository.save(likeHate);
        }
        int likeCount = boardService.getBoardLikeCount(boardId);
        int hateCount = boardService.getBoardHateCount(boardId);

        return new BoardLikeHateDto(likeCount, hateCount,likeHate.isLike(),likeHate.isHate());
    }

    public boolean isLiked(int boardId, Long idx) {
        BoardLikeHate likeHate = boardLikeHateRepository.findByBoardBoardIdAndMemberIdx(boardId, idx);
        return likeHate != null && likeHate.isLike();
    }

    public boolean isHated(int boardId, Long idx) {
        BoardLikeHate likeHate = boardLikeHateRepository.findByBoardBoardIdAndMemberIdx(boardId, idx);
        return likeHate != null && likeHate.isHate();
    }

}

