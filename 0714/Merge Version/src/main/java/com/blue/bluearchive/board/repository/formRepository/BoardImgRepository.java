    package com.blue.bluearchive.board.repository.formRepository;

    import com.blue.bluearchive.board.entity.Board;
    import com.blue.bluearchive.board.entity.BoardImg;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.List;

    public interface BoardImgRepository extends JpaRepository<BoardImg,Integer> {
        List<BoardImg> findByBoardBoardIdOrderByBoardImgIdAsc(Integer boardId);

        BoardImg findByBoardImgUrl(String boardImgUrl);
    }
