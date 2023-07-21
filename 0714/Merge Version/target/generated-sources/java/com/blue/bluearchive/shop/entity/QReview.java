package com.blue.bluearchive.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 1703210392L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath nickName = createString("nickName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final NumberPath<Integer> reportsCount = createNumber("reportsCount", Integer.class);

    public final ListPath<com.blue.bluearchive.report_shop.entity.ReportShop, com.blue.bluearchive.report_shop.entity.QReportShop> reportShop = this.<com.blue.bluearchive.report_shop.entity.ReportShop, com.blue.bluearchive.report_shop.entity.QReportShop>createList("reportShop", com.blue.bluearchive.report_shop.entity.ReportShop.class, com.blue.bluearchive.report_shop.entity.QReportShop.class, PathInits.DIRECT2);

    public final ListPath<ReviewImg, QReviewImg> reviewImgs = this.<ReviewImg, QReviewImg>createList("reviewImgs", ReviewImg.class, QReviewImg.class, PathInits.DIRECT2);

    public final StringPath star = createString("star");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item")) : null;
    }

}

