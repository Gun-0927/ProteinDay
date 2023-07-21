package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentLikeHate is a Querydsl query type for CommentLikeHate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentLikeHate extends EntityPathBase<CommentLikeHate> {

    private static final long serialVersionUID = 612092714L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentLikeHate commentLikeHate = new QCommentLikeHate("commentLikeHate");

    public final QComment comment;

    public final BooleanPath hate = createBoolean("hate");

    public final BooleanPath like = createBoolean("like");

    public final NumberPath<Integer> likeHateId = createNumber("likeHateId", Integer.class);

    public final com.blue.bluearchive.member.entity.QMember member;

    public QCommentLikeHate(String variable) {
        this(CommentLikeHate.class, forVariable(variable), INITS);
    }

    public QCommentLikeHate(Path<? extends CommentLikeHate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentLikeHate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentLikeHate(PathMetadata metadata, PathInits inits) {
        this(CommentLikeHate.class, metadata, inits);
    }

    public QCommentLikeHate(Class<? extends CommentLikeHate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QComment(forProperty("comment"), inits.get("comment")) : null;
        this.member = inits.isInitialized("member") ? new com.blue.bluearchive.member.entity.QMember(forProperty("member")) : null;
    }

}

