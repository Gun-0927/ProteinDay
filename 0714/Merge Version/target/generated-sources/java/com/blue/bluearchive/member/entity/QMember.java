package com.blue.bluearchive.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -2140796354L;

    public static final QMember member = new QMember("member1");

    public final com.blue.bluearchive.shop.entity.QBaseEntity _super = new com.blue.bluearchive.shop.entity.QBaseEntity(this);

    public final StringPath address = createString("address");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath email = createString("email");

    public final EnumPath<com.blue.bluearchive.constant.Grade> grade = createEnum("grade", com.blue.bluearchive.constant.Grade.class);

    public final StringPath id = createString("id");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final EnumPath<com.blue.bluearchive.constant.MemberStat> memberStat = createEnum("memberStat", com.blue.bluearchive.constant.MemberStat.class);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final StringPath phoneNum = createString("phoneNum");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final EnumPath<com.blue.bluearchive.constant.Role> role = createEnum("role", com.blue.bluearchive.constant.Role.class);

    public final EnumPath<com.blue.bluearchive.constant.ShopStatus> shopStatus = createEnum("shopStatus", com.blue.bluearchive.constant.ShopStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public final ListPath<com.blue.bluearchive.userpage.entity.UserCategoryCount, com.blue.bluearchive.userpage.entity.QUserCategoryCount> userCategoryCount = this.<com.blue.bluearchive.userpage.entity.UserCategoryCount, com.blue.bluearchive.userpage.entity.QUserCategoryCount>createList("userCategoryCount", com.blue.bluearchive.userpage.entity.UserCategoryCount.class, com.blue.bluearchive.userpage.entity.QUserCategoryCount.class, PathInits.DIRECT2);

    public final ListPath<com.blue.bluearchive.userpage.entity.UserCategoryLog, com.blue.bluearchive.userpage.entity.QUserCategoryLog> userCategoryLog = this.<com.blue.bluearchive.userpage.entity.UserCategoryLog, com.blue.bluearchive.userpage.entity.QUserCategoryLog>createList("userCategoryLog", com.blue.bluearchive.userpage.entity.UserCategoryLog.class, com.blue.bluearchive.userpage.entity.QUserCategoryLog.class, PathInits.DIRECT2);

    public final ListPath<com.blue.bluearchive.userpage.entity.UserGraphLog, com.blue.bluearchive.userpage.entity.QUserGraphLog> userGraphLog = this.<com.blue.bluearchive.userpage.entity.UserGraphLog, com.blue.bluearchive.userpage.entity.QUserGraphLog>createList("userGraphLog", com.blue.bluearchive.userpage.entity.UserGraphLog.class, com.blue.bluearchive.userpage.entity.QUserGraphLog.class, PathInits.DIRECT2);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

