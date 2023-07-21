package com.blue.bluearchive.admin.repository;

import com.blue.bluearchive.admin.dto.AdminSearchDto;
import com.blue.bluearchive.board.entity.Board;
import com.blue.bluearchive.board.entity.QBoard;
import com.blue.bluearchive.constant.MemberStat;
import com.blue.bluearchive.constant.ShopStatus;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.entity.QMember;
import com.blue.bluearchive.report.entity.QReport;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class AdminUserCareAdminUserCareRepositoryCustomImpl implements AdminUserCareAdminUserCareRepositoryCustom{
    private JPAQueryFactory queryFactory;
    public AdminUserCareAdminUserCareRepositoryCustomImpl(EntityManager em){
        this.queryFactory=new JPAQueryFactory(em);
    }
    //검색 조건 맞은거 찾아냄
    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        System.out.println("검색결과==========="+searchBy+searchQuery);
        if(StringUtils.equals("userName",searchBy)){
            System.out.println("userName일떄 이거");
            return QMember.member.name.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("userId",searchBy)) {
            System.out.println("userId 이거");
            return QMember.member.id.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("userEmail",searchBy)){
            System.out.println("useremail 이거");
            return QMember.member.email.like("%"+searchQuery+"%");
        }
        System.out.println("검색결과가 널이면 이거 실행되겠지");
        return null;
    }
    
    @Override
    public Page<Member> getMemberCaretPage(AdminSearchDto commentSearchDto, Pageable pageable) {
        QMember qMember = QMember.member;
        QReport qReport = QReport.report;

        List<Member> content = queryFactory
                .selectFrom(qMember)
                .leftJoin(qReport).on(qMember.createdBy.eq(qReport.targetCreatedBy).and(qReport.reportStatus.eq(false)))
                .where(searchByLike(commentSearchDto.getSearchBy(), commentSearchDto.getSearchQuery()))
                .groupBy(qMember.idx)
                .orderBy(qReport.count().coalesce(0L).desc(), qMember.idx.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(qMember)
                .where(searchByLike(commentSearchDto.getSearchBy(), commentSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Member> getMemberPage(String categoryId, AdminSearchDto boardSearchDto, Pageable pageable) {
        QReport qReport = QReport.report;

        List<Member> content = queryFactory
                .selectFrom(QMember.member)
                .leftJoin(qReport).on(QMember.member.eq(QMember.member).and(qReport.reportStatus.eq(false)))
                .where(QMember.member.memberStat.eq(MemberStat.fromString(categoryId)))
                .where(searchByLike(boardSearchDto.getSearchBy(), boardSearchDto.getSearchQuery()))
                .groupBy(QMember.member.idx)
                .orderBy(qReport.count().coalesce(0L).desc(), QMember.member.idx.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QMember.member)
                .where(QMember.member.memberStat.eq(MemberStat.fromString(categoryId)))
                .where(searchByLike(boardSearchDto.getSearchBy(), boardSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }



    @Override
    public Page<Member> getSellerWaitingMemberPage(Pageable pageable) {

        List<Member> content = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.shopStatus.eq(ShopStatus.SELLERWAITING))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QMember.member)
                .where(QMember.member.shopStatus.eq(ShopStatus.SELLERWAITING))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }


    @Override
    public Page<Member> getShopMemberPage(String shopState,AdminSearchDto adminSearchDto, Pageable pageable) {

        ShopStatus shopStatus = ShopStatus.CONSUMER;
        if(shopState.equals("SELLER")){
            shopStatus = ShopStatus.SELLER;
        }

        List<Member> content = queryFactory
                .selectFrom(QMember.member)
                .where(QMember.member.shopStatus.eq(shopStatus))
                .where(searchByLike(adminSearchDto.getSearchBy(), adminSearchDto.getSearchQuery()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(QMember.member)
                .where(QMember.member.shopStatus.eq(shopStatus))
                .where(searchByLike(adminSearchDto.getSearchBy(), adminSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
