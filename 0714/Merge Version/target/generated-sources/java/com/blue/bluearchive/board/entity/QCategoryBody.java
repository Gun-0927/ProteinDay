package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryBody is a Querydsl query type for CategoryBody
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryBody extends EntityPathBase<CategoryBody> {

    private static final long serialVersionUID = -1884984906L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryBody categoryBody = new QCategoryBody("categoryBody");

    public final QBoard board;

    public final NumberPath<Integer> categoryBodyId = createNumber("categoryBodyId", Integer.class);

    public QCategoryBody(String variable) {
        this(CategoryBody.class, forVariable(variable), INITS);
    }

    public QCategoryBody(Path<? extends CategoryBody> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryBody(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryBody(PathMetadata metadata, PathInits inits) {
        this(CategoryBody.class, metadata, inits);
    }

    public QCategoryBody(Class<? extends CategoryBody> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

