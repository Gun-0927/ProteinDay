package com.blue.bluearchive.userpage.repository;

import com.blue.bluearchive.admin.entity.Category;
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

public class UserCommentPageRepositoryCustomImpl implements UserCommentPageRepositoryCustom{
    private JPAQueryFactory queryFactory;
    public UserCommentPageRepositoryCustomImpl(EntityManager em){
        this.queryFactory=new JPAQueryFactory(em);
    }
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("createdBy",searchBy)) {
            return QComment.comment.createdBy.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("commentContent",searchBy)){
            return QComment.comment.commentContent.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("boardTitle",searchBy)){
            return QComment.comment.board.boardTitle.like("%"+searchQuery+"%");
        }
        return null;
    }
    @Override
    public Page<Comment> getCreatedByCommentPage(String createdBy, SearchDto searchDto, Pageable pageable) {
        List<Comment> content=queryFactory
                .selectFrom(QComment.comment)
                .where(QComment.comment.createdBy.eq(createdBy)
                        .and(searchByLike(searchDto.getSearchBy(),searchDto.getSearchQuery()))
                ).orderBy(QComment.comment.commentId.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.select(Wildcard.count).from(QComment.comment)
                .where(QComment.comment.createdBy.eq(createdBy)
                        .and(searchByLike(searchDto.getSearchBy(),searchDto.getSearchQuery()))
                ).fetchOne();
        return new PageImpl<>(content,pageable,total);
    }
    @Override
    public Page<Comment> getCreatedByCommentPage(Category category, String createdBy, SearchDto searchDto, Pageable pageable) {
        List<Comment> content=queryFactory
                .selectFrom(QComment.comment)
                .where(QComment.comment.createdBy.eq(createdBy)
                        .and(QComment.comment.board.category.categoryId.eq(category.getCategoryId()))
                        .and(searchByLike(searchDto.getSearchBy(),searchDto.getSearchQuery()))
                ).orderBy(QComment.comment.commentId.desc())
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.select(Wildcard.count).from(QComment.comment)
                .where(QComment.comment.createdBy.eq(createdBy)
                        .and(QComment.comment.board.category.categoryId.eq(category.getCategoryId()))
                        .and(searchByLike(searchDto.getSearchBy(),searchDto.getSearchQuery()))
                ).fetchOne();
        return new PageImpl<>(content,pageable,total);
    }
}
