package com.blue.bluearchive.admin.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdminCommunityCare is a Querydsl query type for AdminCommunityCare
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminCommunityCare extends EntityPathBase<AdminCommunityCare> {

    private static final long serialVersionUID = -197584854L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdminCommunityCare adminCommunityCare = new QAdminCommunityCare("adminCommunityCare");

    public final NumberPath<Integer> boardCount = createNumber("boardCount", Integer.class);

    public final QCategory category;

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> logTime = createDateTime("logTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> statisticsId = createNumber("statisticsId", Long.class);

    public final NumberPath<Integer> totalViews = createNumber("totalViews", Integer.class);

    public QAdminCommunityCare(String variable) {
        this(AdminCommunityCare.class, forVariable(variable), INITS);
    }

    public QAdminCommunityCare(Path<? extends AdminCommunityCare> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdminCommunityCare(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdminCommunityCare(PathMetadata metadata, PathInits inits) {
        this(AdminCommunityCare.class, metadata, inits);
    }

    public QAdminCommunityCare(Class<? extends AdminCommunityCare> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
    }

}

