package com.blue.bluearchive.userpage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserCategoryLog is a Querydsl query type for UserCategoryLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserCategoryLog extends EntityPathBase<UserCategoryLog> {

    private static final long serialVersionUID = -645837193L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserCategoryLog userCategoryLog = new QUserCategoryLog("userCategoryLog");

    public final com.blue.bluearchive.admin.entity.QCategory category;

    public final NumberPath<Integer> categoryCount = createNumber("categoryCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> logTime = createDateTime("logTime", java.time.LocalDateTime.class);

    public final com.blue.bluearchive.member.entity.QMember member;

    public final NumberPath<Integer> userCategoryLogId = createNumber("userCategoryLogId", Integer.class);

    public QUserCategoryLog(String variable) {
        this(UserCategoryLog.class, forVariable(variable), INITS);
    }

    public QUserCategoryLog(Path<? extends UserCategoryLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserCategoryLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserCategoryLog(PathMetadata metadata, PathInits inits) {
        this(UserCategoryLog.class, metadata, inits);
    }

    public QUserCategoryLog(Class<? extends UserCategoryLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.blue.bluearchive.admin.entity.QCategory(forProperty("category")) : null;
        this.member = inits.isInitialized("member") ? new com.blue.bluearchive.member.entity.QMember(forProperty("member")) : null;
    }

}

