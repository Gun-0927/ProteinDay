package com.blue.bluearchive.board.repository;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.dto.BoardSearchDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.QBoard;
import com.blue.bluearchive.userpage.dto.UserPageBoardDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{

    private JPAQueryFactory queryFactory;
    public BoardRepositoryCustomImpl(EntityManager em){
        this.queryFactory=new JPAQueryFactory(em);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("boardTitle",searchBy)){
            return QBoard.board.boardTitle.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("createdBy",searchBy)) {
            return QBoard.board.createdBy.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("boardContent",searchBy)){
            return QBoard.board.boardContent.like("%"+searchQuery+"%");
        }
        return null;
    }

    @Override
    public Page<Board> getBoardPage(BoardSearchDto boardSearchDto, Pageable pageable) {
        List<Board> content=queryFactory
                .selectFrom(QBoard.board)
                .where(searchByLike(boardSearchDto.getSearchBy(),boardSearchDto.getSearchQuery())
                ).orderBy(QBoard.board.boardId.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.select(Wildcard.count).from(QBoard.board)
                .where(searchByLike(boardSearchDto.getSearchBy(),boardSearchDto.getSearchQuery())
                ).fetchOne();
        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public Page<Board> getCategoryBoardPage(Category category, BoardSearchDto boardSearchDto, Pageable pageable) {
        List<Board> content=queryFactory
                .selectFrom(QBoard.board)
                .where(QBoard.board.category.categoryId.eq(category.getCategoryId())
                .and(searchByLike(boardSearchDto.getSearchBy(),boardSearchDto.getSearchQuery()))
                ).orderBy(QBoard.board.boardId.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.select(Wildcard.count).from(QBoard.board)
                .where(QBoard.board.category.categoryId.eq(category.getCategoryId())
                        .and(searchByLike(boardSearchDto.getSearchBy(),boardSearchDto.getSearchQuery()))
                ).fetchOne();

        return new PageImpl<>(content,pageable,total);
    }

}
