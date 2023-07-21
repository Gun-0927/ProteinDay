package com.blue.bluearchive.admin.repository;

import com.blue.bluearchive.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AdminUserCareRepository extends JpaRepository<Member,Long>, QuerydslPredicateExecutor<Member>,
        AdminUserCareAdminUserCareRepositoryCustom {


}
