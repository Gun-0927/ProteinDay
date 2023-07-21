package com.blue.bluearchive.userpage.repository;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.board.entity.Comment;
import com.blue.bluearchive.board.entity.CommentsComment;
import com.blue.bluearchive.board.entity.QComment;
import com.blue.bluearchive.board.entity.QCommentsComment;
import com.blue.bluearchive.userpage.dto.SearchDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class UserCommentsCommentPageRepositoryCustomImpl implements UserCommentsCommentPageRepositoryCustom{
    private JPAQueryFactory queryFactory;
    public UserCommentsCommentPageRepositoryCustomImpl(EntityManager em){
        this.queryFactory=new JPAQueryFactory(em);
    }
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("createdBy",searchBy)) {
            return QCommentsComment.commentsComment.createdBy.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("commentsCommentContent",searchBy)){
            return QCommentsComment.commentsComment.commentsCommentContent.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("commentContent",searchBy)){
            return QCommentsComment.commentsComment.comment.commentContent.like("%"+searchQuery+"%");
        }
        return null;
    }

    @Override
    public Page<CommentsComment> getCreatedByCommentsCommentPage(String createdBy, SearchDto searchDto, Pageable pageable) {
        List<CommentsComment> content=queryFactory
                .selectFrom(QCommentsComment.commentsComment)
                .where(QCommentsComment.commentsComment.createdBy.eq(createdBy)
                        .and(searchByLike(searchDto.getSearchBy(),searchDto.getSearchQuery()))
                ).orderBy(QCommentsComment.commentsComment.commentsCommentId.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.select(Wildcard.count).from(QCommentsComment.commentsComment)
                .where(QCommentsComment.commentsComment.createdBy.eq(createdBy)
                        .and(searchByLike(searchDto.getSearchBy(),searchDto.getSearchQuery()))
                ).fetchOne();
        return new PageImpl<>(content,pageable,total);
    }


}
