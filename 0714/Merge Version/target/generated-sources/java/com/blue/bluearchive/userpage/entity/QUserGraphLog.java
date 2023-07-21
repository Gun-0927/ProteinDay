package com.blue.bluearchive.userpage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserGraphLog is a Querydsl query type for UserGraphLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserGraphLog extends EntityPathBase<UserGraphLog> {

    private static final long serialVersionUID = 699488165L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserGraphLog userGraphLog = new QUserGraphLog("userGraphLog");

    public final DateTimePath<java.time.LocalDateTime> logTime = createDateTime("logTime", java.time.LocalDateTime.class);

    public final com.blue.bluearchive.member.entity.QMember member;

    public final NumberPath<Double> userGraphBmi = createNumber("userGraphBmi", Double.class);

    public final NumberPath<Integer> userGraphId = createNumber("userGraphId", Integer.class);

    public final NumberPath<Double> userGraphIntakeCal = createNumber("userGraphIntakeCal", Double.class);

    public final NumberPath<Double> userGraphSubtractCal = createNumber("userGraphSubtractCal", Double.class);

    public final NumberPath<Double> userGraphWeight = createNumber("userGraphWeight", Double.class);

    public QUserGraphLog(String variable) {
        this(UserGraphLog.class, forVariable(variable), INITS);
    }

    public QUserGraphLog(Path<? extends UserGraphLog> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserGraphLog(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserGraphLog(PathMetadata metadata, PathInits inits) {
        this(UserGraphLog.class, metadata, inits);
    }

    public QUserGraphLog(Class<? extends UserGraphLog> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.blue.bluearchive.member.entity.QMember(forProperty("member")) : null;
    }

}

