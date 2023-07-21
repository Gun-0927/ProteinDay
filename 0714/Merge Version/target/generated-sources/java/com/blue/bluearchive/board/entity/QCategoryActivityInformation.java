package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategoryActivityInformation is a Querydsl query type for CategoryActivityInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryActivityInformation extends EntityPathBase<CategoryActivityInformation> {

    private static final long serialVersionUID = 1319051273L;

    public static final QCategoryActivityInformation categoryActivityInformation = new QCategoryActivityInformation("categoryActivityInformation");

    public final NumberPath<Integer> categoryActivityInformationId = createNumber("categoryActivityInformationId", Integer.class);

    public final NumberPath<Float> categoryActivityInformationMet = createNumber("categoryActivityInformationMet", Float.class);

    public final StringPath categoryActivityInformationName = createString("categoryActivityInformationName");

    public QCategoryActivityInformation(String variable) {
        super(CategoryActivityInformation.class, forVariable(variable));
    }

    public QCategoryActivityInformation(Path<? extends CategoryActivityInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategoryActivityInformation(PathMetadata metadata) {
        super(CategoryActivityInformation.class, metadata);
    }

}

