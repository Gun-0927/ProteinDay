package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryActivityImg is a Querydsl query type for CategoryActivityImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryActivityImg extends EntityPathBase<CategoryActivityImg> {

    private static final long serialVersionUID = -1946325088L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryActivityImg categoryActivityImg = new QCategoryActivityImg("categoryActivityImg");

    public final QCategoryActivity categoryActivity;

    public final NumberPath<Integer> categoryActivityImgId = createNumber("categoryActivityImgId", Integer.class);

    public final StringPath categoryActivityImgUrl = createString("categoryActivityImgUrl");

    public QCategoryActivityImg(String variable) {
        this(CategoryActivityImg.class, forVariable(variable), INITS);
    }

    public QCategoryActivityImg(Path<? extends CategoryActivityImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryActivityImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryActivityImg(PathMetadata metadata, PathInits inits) {
        this(CategoryActivityImg.class, metadata, inits);
    }

    public QCategoryActivityImg(Class<? extends CategoryActivityImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.categoryActivity = inits.isInitialized("categoryActivity") ? new QCategoryActivity(forProperty("categoryActivity"), inits.get("categoryActivity")) : null;
    }

}

