package com.blue.bluearchive.report.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReport is a Querydsl query type for Report
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReport extends EntityPathBase<Report> {

    private static final long serialVersionUID = -1651635726L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReport report = new QReport("report");

    public final com.blue.bluearchive.shop.entity.QBaseEntity _super = new com.blue.bluearchive.shop.entity.QBaseEntity(this);

    public final com.blue.bluearchive.board.entity.QBoard board;

    public final com.blue.bluearchive.board.entity.QComment comment;

    public final com.blue.bluearchive.board.entity.QCommentsComment commentsComment;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final StringPath reportCategory = createString("reportCategory");

    public final StringPath reportContent = createString("reportContent");

    public final NumberPath<Integer> reportId = createNumber("reportId", Integer.class);

    public final BooleanPath reportStatus = createBoolean("reportStatus");

    public final StringPath targetCreatedBy = createString("targetCreatedBy");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QReport(String variable) {
        this(Report.class, forVariable(variable), INITS);
    }

    public QReport(Path<? extends Report> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReport(PathMetadata metadata, PathInits inits) {
        this(Report.class, metadata, inits);
    }

    public QReport(Class<? extends Report> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new com.blue.bluearchive.board.entity.QBoard(forProperty("board"), inits.get("board")) : null;
        this.comment = inits.isInitialized("comment") ? new com.blue.bluearchive.board.entity.QComment(forProperty("comment"), inits.get("comment")) : null;
        this.commentsComment = inits.isInitialized("commentsComment") ? new com.blue.bluearchive.board.entity.QCommentsComment(forProperty("commentsComment"), inits.get("commentsComment")) : null;
    }

}

