package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentsCommentLikeHate is a Querydsl query type for CommentsCommentLikeHate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentsCommentLikeHate extends EntityPathBase<CommentsCommentLikeHate> {

    private static final long serialVersionUID = -1089825642L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentsCommentLikeHate commentsCommentLikeHate = new QCommentsCommentLikeHate("commentsCommentLikeHate");

    public final QCommentsComment commentsComment;

    public final BooleanPath hate = createBoolean("hate");

    public final BooleanPath like = createBoolean("like");

    public final NumberPath<Integer> likeHateId = createNumber("likeHateId", Integer.class);

    public final com.blue.bluearchive.member.entity.QMember member;

    public QCommentsCommentLikeHate(String variable) {
        this(CommentsCommentLikeHate.class, forVariable(variable), INITS);
    }

    public QCommentsCommentLikeHate(Path<? extends CommentsCommentLikeHate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentsCommentLikeHate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentsCommentLikeHate(PathMetadata metadata, PathInits inits) {
        this(CommentsCommentLikeHate.class, metadata, inits);
    }

    public QCommentsCommentLikeHate(Class<? extends CommentsCommentLikeHate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.commentsComment = inits.isInitialized("commentsComment") ? new QCommentsComment(forProperty("commentsComment"), inits.get("commentsComment")) : null;
        this.member = inits.isInitialized("member") ? new com.blue.bluearchive.member.entity.QMember(forProperty("member")) : null;
    }

}

