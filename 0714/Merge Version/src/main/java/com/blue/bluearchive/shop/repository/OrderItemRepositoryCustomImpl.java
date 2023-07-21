package com.blue.bluearchive.shop.repository;

import com.blue.bluearchive.shop.entity.OrderItem;
import com.blue.bluearchive.shop.entity.QOrderItem;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

public class OrderItemRepositoryCustomImpl implements OrderItemRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public OrderItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<OrderItem> getItemSellerHistPage(String itemSellerPersonIdx,Pageable pageable) {
        QOrderItem orderItem = QOrderItem.orderItem;
        List<OrderItem> content=queryFactory
                .selectFrom(QOrderItem.orderItem)
                .where(orderItem.ItemSellerPersonIdx.eq(itemSellerPersonIdx))
                .orderBy(QOrderItem.orderItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count).from(QOrderItem.orderItem)
                .where(orderItem.ItemSellerPersonIdx.eq(itemSellerPersonIdx))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }



    @Override
    public Page<OrderItem> getItemBuyerHistPage(String createBy,Pageable pageable) {
        QOrderItem orderItem = QOrderItem.orderItem;
        List<OrderItem> content=queryFactory
                .selectFrom(QOrderItem.orderItem)
                .where(orderItem.createdBy.eq(createBy))
                .orderBy(QOrderItem.orderItem.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count).from(QOrderItem.orderItem)
                .where(orderItem.createdBy.eq(createBy))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
