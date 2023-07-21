package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryBodyImg is a Querydsl query type for CategoryBodyImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryBodyImg extends EntityPathBase<CategoryBodyImg> {

    private static final long serialVersionUID = 1112134189L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryBodyImg categoryBodyImg = new QCategoryBodyImg("categoryBodyImg");

    public final QCategoryBody categoryBody;

    public final NumberPath<Integer> categoryBodyImgId = createNumber("categoryBodyImgId", Integer.class);

    public final StringPath categoryBodyImgUrl = createString("categoryBodyImgUrl");

    public QCategoryBodyImg(String variable) {
        this(CategoryBodyImg.class, forVariable(variable), INITS);
    }

    public QCategoryBodyImg(Path<? extends CategoryBodyImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryBodyImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryBodyImg(PathMetadata metadata, PathInits inits) {
        this(CategoryBodyImg.class, metadata, inits);
    }

    public QCategoryBodyImg(Class<? extends CategoryBodyImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.categoryBody = inits.isInitialized("categoryBody") ? new QCategoryBody(forProperty("categoryBody"), inits.get("categoryBody")) : null;
    }

}

