package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryFood is a Querydsl query type for CategoryFood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryFood extends EntityPathBase<CategoryFood> {

    private static final long serialVersionUID = -1884865422L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryFood categoryFood = new QCategoryFood("categoryFood");

    public final QBoard board;

    public final NumberPath<Integer> categoryFoodId = createNumber("categoryFoodId", Integer.class);

    public QCategoryFood(String variable) {
        this(CategoryFood.class, forVariable(variable), INITS);
    }

    public QCategoryFood(Path<? extends CategoryFood> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryFood(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryFood(PathMetadata metadata, PathInits inits) {
        this(CategoryFood.class, metadata, inits);
    }

    public QCategoryFood(Class<? extends CategoryFood> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.board = inits.isInitialized("board") ? new QBoard(forProperty("board"), inits.get("board")) : null;
    }

}

