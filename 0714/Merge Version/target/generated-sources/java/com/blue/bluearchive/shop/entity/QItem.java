package com.blue.bluearchive.shop.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 1391460691L;

    public static final QItem item = new QItem("item");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final ListPath<CartItem, QCartItem> cartItems = this.<CartItem, QCartItem>createList("cartItems", CartItem.class, QCartItem.class, PathInits.DIRECT2);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.blue.bluearchive.shop_inquiry.entity.Inquiry, com.blue.bluearchive.shop_inquiry.entity.QInquiry> inquiry = this.<com.blue.bluearchive.shop_inquiry.entity.Inquiry, com.blue.bluearchive.shop_inquiry.entity.QInquiry>createList("inquiry", com.blue.bluearchive.shop_inquiry.entity.Inquiry.class, com.blue.bluearchive.shop_inquiry.entity.QInquiry.class, PathInits.DIRECT2);

    public final EnumPath<com.blue.bluearchive.constant.ItemCategory> itemCategory = createEnum("itemCategory", com.blue.bluearchive.constant.ItemCategory.class);

    public final StringPath itemDetail = createString("itemDetail");

    public final ListPath<ItemImg, QItemImg> itemImg = this.<ItemImg, QItemImg>createList("itemImg", ItemImg.class, QItemImg.class, PathInits.DIRECT2);

    public final StringPath itemNm = createString("itemNm");

    public final EnumPath<com.blue.bluearchive.constant.ItemSellStatus> itemSellStatus = createEnum("itemSellStatus", com.blue.bluearchive.constant.ItemSellStatus.class);

    public final EnumPath<com.blue.bluearchive.constant.ItemUseability> itemUseability = createEnum("itemUseability", com.blue.bluearchive.constant.ItemUseability.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final ListPath<OrderItem, QOrderItem> orderItems = this.<OrderItem, QOrderItem>createList("orderItems", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final ListPath<com.blue.bluearchive.report_shop.entity.ReportShop, com.blue.bluearchive.report_shop.entity.QReportShop> reportShop = this.<com.blue.bluearchive.report_shop.entity.ReportShop, com.blue.bluearchive.report_shop.entity.QReportShop>createList("reportShop", com.blue.bluearchive.report_shop.entity.ReportShop.class, com.blue.bluearchive.report_shop.entity.QReportShop.class, PathInits.DIRECT2);

    public final NumberPath<Integer> stockNumber = createNumber("stockNumber", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public QItem(String variable) {
        super(Item.class, forVariable(variable));
    }

    public QItem(Path<? extends Item> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem(PathMetadata metadata) {
        super(Item.class, metadata);
    }

}

