package com.blue.bluearchive.admin.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = 1442510941L;

    public static final QCategory category = new QCategory("category");

    public final com.blue.bluearchive.shop.entity.QBaseEntity _super = new com.blue.bluearchive.shop.entity.QBaseEntity(this);

    public final ListPath<com.blue.bluearchive.board.entity.Board, com.blue.bluearchive.board.entity.QBoard> board = this.<com.blue.bluearchive.board.entity.Board, com.blue.bluearchive.board.entity.QBoard>createList("board", com.blue.bluearchive.board.entity.Board.class, com.blue.bluearchive.board.entity.QBoard.class, PathInits.DIRECT2);

    public final NumberPath<Integer> boardCount = createNumber("boardCount", Integer.class);

    public final NumberPath<Integer> categoryId = createNumber("categoryId", Integer.class);

    public final StringPath categoryName = createString("categoryName");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final NumberPath<Integer> totalReports = createNumber("totalReports", Integer.class);

    public final NumberPath<Integer> totalViews = createNumber("totalViews", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public final ListPath<com.blue.bluearchive.userpage.entity.UserCategoryCount, com.blue.bluearchive.userpage.entity.QUserCategoryCount> userCategoryCount = this.<com.blue.bluearchive.userpage.entity.UserCategoryCount, com.blue.bluearchive.userpage.entity.QUserCategoryCount>createList("userCategoryCount", com.blue.bluearchive.userpage.entity.UserCategoryCount.class, com.blue.bluearchive.userpage.entity.QUserCategoryCount.class, PathInits.DIRECT2);

    public final ListPath<com.blue.bluearchive.userpage.entity.UserCategoryLog, com.blue.bluearchive.userpage.entity.QUserCategoryLog> userCategoryLog = this.<com.blue.bluearchive.userpage.entity.UserCategoryLog, com.blue.bluearchive.userpage.entity.QUserCategoryLog>createList("userCategoryLog", com.blue.bluearchive.userpage.entity.UserCategoryLog.class, com.blue.bluearchive.userpage.entity.QUserCategoryLog.class, PathInits.DIRECT2);

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

