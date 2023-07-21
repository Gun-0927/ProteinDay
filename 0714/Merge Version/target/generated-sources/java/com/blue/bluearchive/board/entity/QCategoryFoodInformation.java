package com.blue.bluearchive.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCategoryFoodInformation is a Querydsl query type for CategoryFoodInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategoryFoodInformation extends EntityPathBase<CategoryFoodInformation> {

    private static final long serialVersionUID = -627636902L;

    public static final QCategoryFoodInformation categoryFoodInformation = new QCategoryFoodInformation("categoryFoodInformation");

    public final NumberPath<Float> categoryFoodInformationCarb = createNumber("categoryFoodInformationCarb", Float.class);

    public final NumberPath<Float> categoryFoodInformationFat = createNumber("categoryFoodInformationFat", Float.class);

    public final NumberPath<Integer> categoryFoodInformationId = createNumber("categoryFoodInformationId", Integer.class);

    public final NumberPath<Float> categoryFoodInformationKcal = createNumber("categoryFoodInformationKcal", Float.class);

    public final StringPath categoryFoodInformationName = createString("categoryFoodInformationName");

    public final NumberPath<Float> categoryFoodInformationProtein = createNumber("categoryFoodInformationProtein", Float.class);

    public final NumberPath<Float> categoryFoodInformationSize = createNumber("categoryFoodInformationSize", Float.class);

    public QCategoryFoodInformation(String variable) {
        super(CategoryFoodInformation.class, forVariable(variable));
    }

    public QCategoryFoodInformation(Path<? extends CategoryFoodInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategoryFoodInformation(PathMetadata metadata) {
        super(CategoryFoodInformation.class, metadata);
    }

}

