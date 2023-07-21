package com.blue.bluearchive.userpage.repository;

import com.blue.bluearchive.admin.entity.Category;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.userpage.entity.UserCategoryCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryCountRepository extends JpaRepository<UserCategoryCount,Integer> {
    UserCategoryCount findByMemberAndCategory(Member member, Category category);
}
