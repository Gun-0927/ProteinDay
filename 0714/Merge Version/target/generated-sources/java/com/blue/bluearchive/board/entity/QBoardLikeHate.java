package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoardLikeHate is a Querydsl query type for BoardLikeHate
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardLikeHate extends EntityPathBase<BoardLikeHate> {

    private static final long serialVersionUID = 1514561329L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoardLikeHate boardLikeHate = new QBoardLikeHate("boardLikeHate");

    public final QBoard board;

    public final BooleanPath hate = createBoolean("hate");

    public final BooleanPath like = createBoolean("like");

    public final NumberPath<Integer> likeHateId = createNumber("likeHateId", Integer.class);

    public final com.blue.bluearchive.member.entity.QMember member;

    public QBoardLikeHate(String variable) {
        this(BoardLikeHate.class, forVariable(variable), INITS);
    }

    public QBoardLikeHate(Path<? extends BoardLikeHate> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoardLikeHate(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoardLikeHate(PathMetadata metadata, PathInits inits) {
        this(BoardLikeHate.class, metadata, inits);
    }

    public QBoardLikeHate(Class<? extends BoardLikeHate> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
        this.member = inits.isInitialized("member") ? new com.blue.bluearchive.member.entity.QMember(forProperty("member")) : null;
    }

}

