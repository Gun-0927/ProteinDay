package com.blue.bluearchive.admin.repository;

import com.blue.bluearchive.admin.dto.AdminSearchDto;
import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.QBoard;
import com.blue.bluearchive.board.entity.QComment;
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
import java.util.Collections;
import java.util.List;

public class AdminCommentRepositoryCustomImpl implements AdminCommentRepositoryCustom {
    private JPAQueryFactory queryFactory;
    private final EntityManager entityManager;
    public AdminCommentRepositoryCustomImpl(EntityManager em){
        this.queryFactory=new JPAQueryFactory(em);
        this.entityManager = em;
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("commentContent",searchBy)){
            return QComment.comment.commentContent.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("createdBy",searchBy)) {
            return QComment.comment.createdBy.like("%"+searchQuery+"%");
        }
        return null;
    }
    @Override
    public Page<Comment> getCommentPage(AdminSearchDto commentSearchDto, Pageable pageable) {
        QReport qReport = QReport.report;

        List<Comment> content = queryFactory
                .selectFrom(QComment.comment)
                .leftJoin(qReport).on(QComment.comment.eq(qReport.comment).and(qReport.reportStatus.eq(false)))
                .where(searchByLike(commentSearchDto.getSearchBy(), commentSearchDto.getSearchQuery()))
                .groupBy(QComment.comment.commentId)
                .orderBy(qReport.count().coalesce(0L).desc(), QComment.comment.commentId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QComment.comment)
                .where(searchByLike(commentSearchDto.getSearchBy(), commentSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }


    @Override
    public Page<Comment> getBoardCommentPage(int categoryId, AdminSearchDto commentSearchDto, Pageable pageable) {
        QReport qReport = QReport.report;

        List<Comment> content = queryFactory
                .selectFrom(QComment.comment)
                .leftJoin(qReport).on(QComment.comment.eq(qReport.comment).and(qReport.reportStatus.eq(false)))
                .where(QComment.comment.board.category.categoryId.eq(categoryId))
                .where(searchByLike(commentSearchDto.getSearchBy(), commentSearchDto.getSearchQuery()))
                .groupBy(QComment.comment.commentId)
                .orderBy(qReport.count().coalesce(0L).desc(), QComment.comment.commentId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QComment.comment)
                .where(QComment.comment.board.category.categoryId.eq(categoryId))
                .where(searchByLike(commentSearchDto.getSearchBy(), commentSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Comment> getMemberPage(Long selectUser, Pageable pageable) {
        QComment comment = QComment.comment;

        String memberId = selectUser.toString();

        List<Comment> content = queryFactory
                .select(comment)
                .from(comment)
                .where(comment.createdBy.eq(memberId))
                .orderBy(comment.commentId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(comment)
                .from(comment)
                .where(comment.createdBy.eq(memberId))
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

}


