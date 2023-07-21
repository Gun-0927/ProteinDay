package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryPlace is a Querydsl query type for CategoryPlace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryPlace extends EntityPathBase<CategoryPlace> {

    private static final long serialVersionUID = 1707846515L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryPlace categoryPlace = new QCategoryPlace("categoryPlace");

    public final QBoard board;

    public final NumberPath<Integer> categoryPlaceId = createNumber("categoryPlaceId", Integer.class);

    public QCategoryPlace(String variable) {
        this(CategoryPlace.class, forVariable(variable), INITS);
    }

    public QCategoryPlace(Path<? extends CategoryPlace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryPlace(PathMetadata metadata, PathInits inits) {
        this(CategoryPlace.class, metadata, inits);
    }

    public QCategoryPlace(Class<? extends CategoryPlace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

