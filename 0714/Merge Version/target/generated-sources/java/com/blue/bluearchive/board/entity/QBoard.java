package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -417109584L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final com.blue.bluearchive.shop.entity.QBaseEntity _super = new com.blue.bluearchive.shop.entity.QBaseEntity(this);

    public final StringPath boardContent = createString("boardContent");

    public final NumberPath<Integer> boardCount = createNumber("boardCount", Integer.class);

    public final NumberPath<Integer> boardHateCount = createNumber("boardHateCount", Integer.class);

    public final NumberPath<Integer> boardId = createNumber("boardId", Integer.class);

    public final ListPath<BoardImg, QBoardImg> boardImg = this.<BoardImg, QBoardImg>createList("boardImg", BoardImg.class, QBoardImg.class, PathInits.DIRECT2);

    public final NumberPath<Integer> boardLikeCount = createNumber("boardLikeCount", Integer.class);

    public final ListPath<BoardLikeHate, QBoardLikeHate> boardLikeHate = this.<BoardLikeHate, QBoardLikeHate>createList("boardLikeHate", BoardLikeHate.class, QBoardLikeHate.class, PathInits.DIRECT2);

    public final NumberPath<Integer> boardPreCount = createNumber("boardPreCount", Integer.class);

    public final NumberPath<Integer> boardReportsCount = createNumber("boardReportsCount", Integer.class);

    public final StringPath boardTitle = createString("boardTitle");

    public final com.blue.bluearchive.admin.entity.QCategory category;

    public final ListPath<Comment, QComment> comment = this.<Comment, QComment>createList("comment", Comment.class, QComment.class, PathInits.DIRECT2);

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final com.blue.bluearchive.member.entity.QMember member;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final ListPath<com.blue.bluearchive.report.entity.Report, com.blue.bluearchive.report.entity.QReport> report = this.<com.blue.bluearchive.report.entity.Report, com.blue.bluearchive.report.entity.QReport>createList("report", com.blue.bluearchive.report.entity.Report.class, com.blue.bluearchive.report.entity.QReport.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.blue.bluearchive.admin.entity.QCategory(forProperty("category")) : null;
        this.member = inits.isInitialized("member") ? new com.blue.bluearchive.member.entity.QMember(forProperty("member")) : null;
    }

}

