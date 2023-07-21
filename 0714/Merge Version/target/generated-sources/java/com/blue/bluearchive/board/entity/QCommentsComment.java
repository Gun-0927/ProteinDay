package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentsComment is a Querydsl query type for CommentsComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommentsComment extends EntityPathBase<CommentsComment> {

    private static final long serialVersionUID = 619828245L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentsComment commentsComment = new QCommentsComment("commentsComment");

    public final com.blue.bluearchive.shop.entity.QBaseEntity _super = new com.blue.bluearchive.shop.entity.QBaseEntity(this);

    public final QComment comment;

    public final StringPath commentsCommentContent = createString("commentsCommentContent");

    public final NumberPath<Integer> commentsCommentHateCount = createNumber("commentsCommentHateCount", Integer.class);

    public final NumberPath<Integer> commentsCommentId = createNumber("commentsCommentId", Integer.class);

    public final ListPath<CommentsCommentLikeHate, QCommentsCommentLikeHate> commentsCommentLikeAndHate = this.<CommentsCommentLikeHate, QCommentsCommentLikeHate>createList("commentsCommentLikeAndHate", CommentsCommentLikeHate.class, QCommentsCommentLikeHate.class, PathInits.DIRECT2);

    public final NumberPath<Integer> commentsCommentLikeCount = createNumber("commentsCommentLikeCount", Integer.class);

    public final NumberPath<Integer> commentsCommentReportsCount = createNumber("commentsCommentReportsCount", Integer.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final ListPath<com.blue.bluearchive.report.entity.Report, com.blue.bluearchive.report.entity.QReport> report = this.<com.blue.bluearchive.report.entity.Report, com.blue.bluearchive.report.entity.QReport>createList("report", com.blue.bluearchive.report.entity.Report.class, com.blue.bluearchive.report.entity.QReport.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QCommentsComment(String variable) {
        this(CommentsComment.class, forVariable(variable), INITS);
    }

    public QCommentsComment(Path<? extends CommentsComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentsComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentsComment(PathMetadata metadata, PathInits inits) {
        this(CommentsComment.class, metadata, inits);
    }

    public QCommentsComment(Class<? extends CommentsComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QComment(forProperty("comment"), inits.get("comment")) : null;
    }

}

