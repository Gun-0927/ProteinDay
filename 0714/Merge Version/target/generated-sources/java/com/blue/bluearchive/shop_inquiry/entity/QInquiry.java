package com.blue.bluearchive.shop_inquiry.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquiry is a Querydsl query type for Inquiry
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquiry extends EntityPathBase<Inquiry> {

    private static final long serialVersionUID = -1932624641L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInquiry inquiry = new QInquiry("inquiry");

    public final com.blue.bluearchive.shop.entity.QBaseEntity _super = new com.blue.bluearchive.shop.entity.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<InquiryAnswer, QInquiryAnswer> inquiryAnswer = this.<InquiryAnswer, QInquiryAnswer>createList("inquiryAnswer", InquiryAnswer.class, QInquiryAnswer.class, PathInits.DIRECT2);

    public final com.blue.bluearchive.shop.entity.QItem item;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final ListPath<com.blue.bluearchive.report_shop.entity.ReportShop, com.blue.bluearchive.report_shop.entity.QReportShop> reportShop = this.<com.blue.bluearchive.report_shop.entity.ReportShop, com.blue.bluearchive.report_shop.entity.QReportShop>createList("reportShop", com.blue.bluearchive.report_shop.entity.ReportShop.class, com.blue.bluearchive.report_shop.entity.QReportShop.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QInquiry(String variable) {
        this(Inquiry.class, forVariable(variable), INITS);
    }

    public QInquiry(Path<? extends Inquiry> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInquiry(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInquiry(PathMetadata metadata, PathInits inits) {
        this(Inquiry.class, metadata, inits);
    }

    public QInquiry(Class<? extends Inquiry> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new com.blue.bluearchive.shop.entity.QItem(forProperty("item")) : null;
    }

}

