package com.blue.bluearchive.userpage.service;


import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.userpage.dto.SearchDto;
import com.blue.bluearchive.userpage.repository.UserBoardBoardPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPageBoardService {
    private final UserBoardBoardPageRepository userBoardRepository;

    //카테고리 전체기준 리스트 가져오기
    @Transactional(readOnly = true)
    public Page<Board> getBoardByCreatedBy(String userIdx, SearchDto boardSearchDto, Pageable pageable){
        return userBoardRepository.getCreatedByBoardPage(userIdx,boardSearchDto,pageable);
    }
    @Transactional(readOnly = true)
    public Page<Board> getBoardByCreatedBy(Category category, String userIdx, SearchDto searchDto, Pageable pageable){
        return userBoardRepository.getCreatedByBoardPage(category,userIdx,searchDto,pageable);
    }


}
