package com.blue.bluearchive.userpage.repository;

import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.userpage.entity.UserGraphLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGraphLogRepository extends JpaRepository<UserGraphLog,Integer> {
    UserGraphLog findTopByMemberOrderByLogTimeDesc(Member member);
    List<UserGraphLog> findAllByMember(Member member);

}
