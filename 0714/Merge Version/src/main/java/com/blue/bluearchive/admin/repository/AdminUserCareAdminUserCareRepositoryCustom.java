package com.blue.bluearchive.admin.repository;

import com.blue.bluearchive.admin.dto.AdminSearchDto;
import com.blue.bluearchive.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminUserCareAdminUserCareRepositoryCustom {

    Page<Member> getMemberCaretPage(AdminSearchDto searchDto, Pageable pageable);

    Page<Member> getMemberPage(String categoryId, AdminSearchDto searchDto, Pageable pageable);
    Page<Member> getSellerWaitingMemberPage(Pageable pageable);
    Page<Member> getShopMemberPage(String shopState, AdminSearchDto adminSearchDto, Pageable pageable);




}
