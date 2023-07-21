package com.blue.bluearchive.userpage.repository;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.QBoard;
import com.blue.bluearchive.board.entity.QComment;
import com.blue.bluearchive.userpage.dto.SearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class UserBoardPageRepositoryCustomImpl implements UserBoardPageRepositoryCustom {


    private JPAQueryFactory queryFactory;
    public UserBoardPageRepositoryCustomImpl(EntityManager em){
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
    public Page<Board> getCreatedByBoardPage(String createdBy, SearchDto searchDto, Pageable pageable) {
        List<Board> content=queryFactory
                .selectFrom(QBoard.board)
                .where(QBoard.board.createdBy.eq(createdBy)
                        .and(searchByLike(searchDto.getSearchBy(),searchDto.getSearchQuery()))
                ).orderBy(QBoard.board.boardId.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.select(Wildcard.count).from(QBoard.board)
                .where(QBoard.board.createdBy.eq(createdBy)
                        .and(searchByLike(searchDto.getSearchBy(),searchDto.getSearchQuery()))
                ).fetchOne();
        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public Page<Board> getCreatedByBoardPage(Category category, String createdBy, SearchDto searchDto, Pageable pageable) {
        List<Board> content=queryFactory
                .selectFrom(QBoard.board)
                .where(QBoard.board.createdBy.eq(createdBy)
                        .and(QBoard.board.category.categoryId.eq(category.getCategoryId()))
                        .and(searchByLike(searchDto.getSearchBy(),searchDto.getSearchQuery()))
                ).orderBy(QBoard.board.boardId.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.select(Wildcard.count).from(QBoard.board)
                .where(QBoard.board.createdBy.eq(createdBy)
                        .and(QBoard.board.category.categoryId.eq(category.getCategoryId()))
                        .and(searchByLike(searchDto.getSearchBy(),searchDto.getSearchQuery()))
                ).fetchOne();

        return new PageImpl<>(content,pageable,total);
    }


}
