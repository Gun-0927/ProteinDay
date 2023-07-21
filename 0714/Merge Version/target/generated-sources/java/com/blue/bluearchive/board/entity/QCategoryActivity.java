package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryActivity is a Querydsl query type for CategoryActivity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryActivity extends EntityPathBase<CategoryActivity> {

    private static final long serialVersionUID = 1053096227L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryActivity categoryActivity = new QCategoryActivity("categoryActivity");

    public final QBoard board;

    public final NumberPath<Integer> categoryActivityId = createNumber("categoryActivityId", Integer.class);

    public QCategoryActivity(String variable) {
        this(CategoryActivity.class, forVariable(variable), INITS);
    }

    public QCategoryActivity(Path<? extends CategoryActivity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryActivity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryActivity(PathMetadata metadata, PathInits inits) {
        this(CategoryActivity.class, metadata, inits);
    }

    public QCategoryActivity(Class<? extends CategoryActivity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

