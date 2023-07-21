package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = -511910231L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment = new QComment("comment");

    public final com.blue.bluearchive.shop.entity.QBaseEntity _super = new com.blue.bluearchive.shop.entity.QBaseEntity(this);

    public final QBoard board;

    public final StringPath commentContent = createString("commentContent");

    public final NumberPath<Integer> commentHateCount = createNumber("commentHateCount", Integer.class);

    public final NumberPath<Integer> commentId = createNumber("commentId", Integer.class);

    public final NumberPath<Integer> commentLikeCount = createNumber("commentLikeCount", Integer.class);

    public final ListPath<CommentLikeHate, QCommentLikeHate> commentLikeHate = this.<CommentLikeHate, QCommentLikeHate>createList("commentLikeHate", CommentLikeHate.class, QCommentLikeHate.class, PathInits.DIRECT2);

    public final NumberPath<Integer> commentReportsCount = createNumber("commentReportsCount", Integer.class);

    public final ListPath<CommentsComment, QCommentsComment> commentsComment = this.<CommentsComment, QCommentsComment>createList("commentsComment", CommentsComment.class, QCommentsComment.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final ListPath<com.blue.bluearchive.report.entity.Report, com.blue.bluearchive.report.entity.QReport> report = this.<com.blue.bluearchive.report.entity.Report, com.blue.bluearchive.report.entity.QReport>createList("report", com.blue.bluearchive.report.entity.Report.class, com.blue.bluearchive.report.entity.QReport.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

