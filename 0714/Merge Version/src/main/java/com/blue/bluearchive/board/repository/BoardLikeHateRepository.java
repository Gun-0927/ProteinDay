package com.blue.bluearchive.board.repository;

import com.blue.bluearchive.board.entity.BoardLikeHate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardLikeHateRepository extends JpaRepository<BoardLikeHate,Integer> {
    BoardLikeHate findByBoardBoardIdAndMemberIdx(int boardId, Long idx);
}
