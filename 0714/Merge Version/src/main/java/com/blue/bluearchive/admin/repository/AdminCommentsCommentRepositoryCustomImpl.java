package com.blue.bluearchive.admin.repository;

import com.blue.bluearchive.admin.dto.AdminSearchDto;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.board.entity.QComment;
import com.blue.bluearchive.board.entity.QCommentsComment;
import com.blue.bluearchive.report.entity.QReport;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
@Slf4j
public class AdminCommentsCommentRepositoryCustomImpl implements AdminCommentsCommentRepositoryCustom {
    private JPAQueryFactory queryFactory;
    private final EntityManager entityManager;
    public AdminCommentsCommentRepositoryCustomImpl(EntityManager em){
        this.queryFactory=new JPAQueryFactory(em);
        this.entityManager = em;
    }
    //검색 조건 맞은거 찾아냄
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("commentsCommentContent",searchBy)){
            return QCommentsComment.commentsComment.commentsCommentContent.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("createdBy",searchBy)) {
            return QCommentsComment.commentsComment.createdBy.like("%"+searchQuery+"%");
        }
        return null;
    }
//    @Override
//    public Page<CommentsComment> getCommentsCommentPage(AdminSearchDto commentSearchDto, Pageable pageable) {
//        List<CommentsComment> content=queryFactory
//                .selectFrom(QCommentsComment.commentsComment)
//                .where(searchByLike(commentSearchDto.getSearchBy(),commentSearchDto.getSearchQuery())
//                ).orderBy(QCommentsComment.commentsComment.commentsCommentId.desc())
//                .offset(pageable.getOffset()).limit(pageable.getPageSize())
//                .fetch(); //전체 반환
//        long total = queryFactory.select(Wildcard.count).from(QCommentsComment.commentsComment)
//                .where(searchByLike(commentSearchDto.getSearchBy(),commentSearchDto.getSearchQuery())
//                ).fetchOne();
//
//        return new PageImpl<>(content,pageable,total);
//    }
@Override
public Page<CommentsComment> getCommentsCommentPage(AdminSearchDto commentSearchDto, Pageable pageable) {
    QReport qReport = QReport.report;

    List<CommentsComment> content = queryFactory
            .selectFrom(QCommentsComment.commentsComment)
            .leftJoin(qReport).on(QCommentsComment.commentsComment.eq(qReport.commentsComment).and(qReport.reportStatus.eq(false)))
            .where(searchByLike(commentSearchDto.getSearchBy(), commentSearchDto.getSearchQuery()))
            .groupBy(QCommentsComment.commentsComment.commentsCommentId)
            .orderBy(qReport.count().coalesce(0L).desc(), QCommentsComment.commentsComment.commentsCommentId.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    long total = queryFactory
            .select(Wildcard.count)
            .from(QCommentsComment.commentsComment)
            .where(searchByLike(commentSearchDto.getSearchBy(), commentSearchDto.getSearchQuery()))
            .fetchOne();

    return new PageImpl<>(content, pageable, total);
}

    @Override
    public Page<CommentsComment> getMemberCommentsComment(Long selectUser, Pageable pageable) {
        QCommentsComment comment = QCommentsComment.commentsComment;
        String memberId = selectUser.toString();
        List<CommentsComment> content = queryFactory
                .select(comment)
                .from(comment)
                .where(comment.createdBy.eq(memberId))
                .orderBy(comment.commentsCommentId.desc())
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
