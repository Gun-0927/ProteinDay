package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategoryFoodImg is a Querydsl query type for CategoryFoodImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryFoodImg extends EntityPathBase<CategoryFoodImg> {

    private static final long serialVersionUID = 376714737L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategoryFoodImg categoryFoodImg = new QCategoryFoodImg("categoryFoodImg");

    public final QCategoryFood categoryFood;

    public final NumberPath<Integer> categoryFoodImgId = createNumber("categoryFoodImgId", Integer.class);

    public final StringPath categoryFoodImgUrl = createString("categoryFoodImgUrl");

    public QCategoryFoodImg(String variable) {
        this(CategoryFoodImg.class, forVariable(variable), INITS);
    }

    public QCategoryFoodImg(Path<? extends CategoryFoodImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategoryFoodImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategoryFoodImg(PathMetadata metadata, PathInits inits) {
        this(CategoryFoodImg.class, metadata, inits);
    }

    public QCategoryFoodImg(Class<? extends CategoryFoodImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.categoryFood = inits.isInitialized("categoryFood") ? new QCategoryFood(forProperty("categoryFood"), inits.get("categoryFood")) : null;
    }

}

