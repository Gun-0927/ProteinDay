package com.blue.bluearchive.admin.repository;

import com.blue.bluearchive.admin.dto.AdminSearchDto;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.dto.BoardSearchDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.QBoard;
import com.blue.bluearchive.board.repository.BoardRepositoryCustom;
import com.blue.bluearchive.member.entity.QMember;
import com.blue.bluearchive.report.entity.QReport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class AdminBoardRepositoryCustomImpl implements AdminBoardRepositoryCustom {
    private JPAQueryFactory queryFactory;
    public AdminBoardRepositoryCustomImpl(EntityManager em){
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
    public Page<Board> getBoardPage(AdminSearchDto boardSearchDto, Pageable pageable) {
        QReport qReport = QReport.report;
        List<Board> content = queryFactory
                .selectFrom(QBoard.board)
                .leftJoin(qReport).on(QBoard.board.boardId.eq(qReport.board.boardId).and(qReport.reportStatus.eq(false)))
                .where(searchByLike(boardSearchDto.getSearchBy(), boardSearchDto.getSearchQuery()))
                .groupBy(QBoard.board.boardId)
                .orderBy(qReport.count().coalesce(0L).desc(), QBoard.board.boardId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QBoard.board)
                .where(searchByLike(boardSearchDto.getSearchBy(), boardSearchDto.getSearchQuery()))
                .fetchOne();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Board> getCategoryBoardPage(int categoryId, AdminSearchDto boardSearchDto, Pageable pageable) {
        QReport qReport = QReport.report;

        List<Board> content = queryFactory
                .selectFrom(QBoard.board)
                .leftJoin(qReport).on(QBoard.board.boardId.eq(qReport.board.boardId).and(qReport.reportStatus.eq(false)))
                .where(QBoard.board.category.categoryId.eq(categoryId))
                .where(searchByLike(boardSearchDto.getSearchBy(), boardSearchDto.getSearchQuery()))
                .groupBy(QBoard.board.boardId)
                .orderBy(qReport.count().coalesce(0L).desc(), QBoard.board.boardId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QBoard.board)
                .where(QBoard.board.category.categoryId.eq(categoryId))
                .where(searchByLike(boardSearchDto.getSearchBy(), boardSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Board> getMemberPage(Long selectUser, Pageable pageable) {
        List<Board> content = queryFactory
                .selectFrom(QBoard.board)
                .where(QBoard.board.member.idx.eq(selectUser))
                .orderBy(QBoard.board.boardId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory
                .select(Wildcard.count)
                .from(QBoard.board)
                .where(QBoard.board.member.idx.eq(selectUser))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
