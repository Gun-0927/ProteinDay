package com.blue.bluearchive.userpage.repository;

import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.userpage.entity.UserCategoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryLogRepository extends JpaRepository<UserCategoryLog,Integer> {
    List<UserCategoryLog> findByMember(Member member);

}
