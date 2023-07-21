package com.blue.bluearchive.userpage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserCategoryCount is a Querydsl query type for UserCategoryCount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserCategoryCount extends EntityPathBase<UserCategoryCount> {

    private static final long serialVersionUID = 2112420738L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserCategoryCount userCategoryCount = new QUserCategoryCount("userCategoryCount");

    public final com.blue.bluearchive.admin.entity.QCategory category;

    public final NumberPath<Integer> categoryAllCount = createNumber("categoryAllCount", Integer.class);

    public final NumberPath<Integer> categoryPreCount = createNumber("categoryPreCount", Integer.class);

    public final com.blue.bluearchive.member.entity.QMember member;

    public final NumberPath<Integer> userCategoryCountId = createNumber("userCategoryCountId", Integer.class);

    public QUserCategoryCount(String variable) {
        this(UserCategoryCount.class, forVariable(variable), INITS);
    }

    public QUserCategoryCount(Path<? extends UserCategoryCount> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserCategoryCount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserCategoryCount(PathMetadata metadata, PathInits inits) {
        this(UserCategoryCount.class, metadata, inits);
    }

    public QUserCategoryCount(Class<? extends UserCategoryCount> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.blue.bluearchive.admin.entity.QCategory(forProperty("category")) : null;
        this.member = inits.isInitialized("member") ? new com.blue.bluearchive.member.entity.QMember(forProperty("member")) : null;
    }

}

