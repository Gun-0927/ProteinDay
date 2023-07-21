package com.blue.bluearchive.report_shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReportShop is a Querydsl query type for ReportShop
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportShop extends EntityPathBase<ReportShop> {

    private static final long serialVersionUID = 572551931L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReportShop reportShop = new QReportShop("reportShop");

    public final com.blue.bluearchive.shop.entity.QBaseEntity _super = new com.blue.bluearchive.shop.entity.QBaseEntity(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final com.blue.bluearchive.shop_inquiry.entity.QInquiry inquiry;

    public final com.blue.bluearchive.shop_inquiry.entity.QInquiryAnswer inquiryAnswer;

    public final com.blue.bluearchive.shop.entity.QItem item;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final StringPath reportCategory = createString("reportCategory");

    public final StringPath reportContent = createString("reportContent");

    public final NumberPath<Integer> reportId = createNumber("reportId", Integer.class);

    public final com.blue.bluearchive.shop.entity.QReview review;

    public final StringPath targetCreatedBy = createString("targetCreatedBy");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QReportShop(String variable) {
        this(ReportShop.class, forVariable(variable), INITS);
    }

    public QReportShop(Path<? extends ReportShop> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReportShop(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReportShop(PathMetadata metadata, PathInits inits) {
        this(ReportShop.class, metadata, inits);
    }

    public QReportShop(Class<? extends ReportShop> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inquiry = inits.isInitialized("inquiry") ? new com.blue.bluearchive.shop_inquiry.entity.QInquiry(forProperty("inquiry"), inits.get("inquiry")) : null;
        this.inquiryAnswer = inits.isInitialized("inquiryAnswer") ? new com.blue.bluearchive.shop_inquiry.entity.QInquiryAnswer(forProperty("inquiryAnswer"), inits.get("inquiryAnswer")) : null;
        this.item = inits.isInitialized("item") ? new com.blue.bluearchive.shop.entity.QItem(forProperty("item")) : null;
        this.review = inits.isInitialized("review") ? new com.blue.bluearchive.shop.entity.QReview(forProperty("review"), inits.get("review")) : null;
    }

}

