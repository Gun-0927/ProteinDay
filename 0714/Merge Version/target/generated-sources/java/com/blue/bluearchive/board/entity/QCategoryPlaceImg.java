package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryPlaceImg is a Querydsl query type for CategoryPlaceImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryPlaceImg extends EntityPathBase<CategoryPlaceImg> {

    private static final long serialVersionUID = 273013584L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryPlaceImg categoryPlaceImg = new QCategoryPlaceImg("categoryPlaceImg");

    public final QCategoryPlace categoryPlace;

    public final NumberPath<Integer> categoryPlaceImgId = createNumber("categoryPlaceImgId", Integer.class);

    public final StringPath categoryPlaceImgUrl = createString("categoryPlaceImgUrl");

    public QCategoryPlaceImg(String variable) {
        this(CategoryPlaceImg.class, forVariable(variable), INITS);
    }

    public QCategoryPlaceImg(Path<? extends CategoryPlaceImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryPlaceImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryPlaceImg(PathMetadata metadata, PathInits inits) {
        this(CategoryPlaceImg.class, metadata, inits);
    }

    public QCategoryPlaceImg(Class<? extends CategoryPlaceImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.categoryPlace = inits.isInitialized("categoryPlace") ? new QCategoryPlace(forProperty("categoryPlace"), inits.get("categoryPlace")) : null;
    }

}

