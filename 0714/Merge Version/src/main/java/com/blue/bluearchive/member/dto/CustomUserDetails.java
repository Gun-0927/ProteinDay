package com.blue.bluearchive.member.dto;

import com.blue.bluearchive.constant.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class CustomUserDetails implements UserDetails, OAuth2User {
    private final Long idx;
    private final String id;
    private final String name;
    private final String nickName;
    private final String email;
    private final String password;
    private final String address;
    private final String phoneNum;
    private final Role role;
    private final Grade grade;
    private final MemberStat memberStat;
    private final String createdBy;
    private final ShopStatus shopStatus;
    //간편로그인 추가
    private final Map<String, Object> attributes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        authorities.add(new SimpleGrantedAuthority("GRADE_" + grade.name()));
        authorities.add(new SimpleGrantedAuthority("STAT_" + memberStat.name()));
        authorities.add(new SimpleGrantedAuthority("SHOP_" + shopStatus.name()));
        return authorities;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return name;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}

/*
package com.blue.bluearchive.member.dto;

import com.blue.bluearchive.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(member.getRole().name().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getName();
    }
    public String getId() {
        return member.getId();
    }
    public String getEmail() {
        return member.getEmail();
    }

    public String getPhoneNum() {
        return member.getPhoneNum();
    }
    public String getAddress() {
        return member.getAddress();
    }
    public String getNickName() {
        return member.getNickName();
    }
    //    public MemberStat getMemberStat() {
//        return member.getMemberStat();
//    }
    public String getCreatedBy() {
        return member.getCreatedBy();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Member getMember() {
        return member;
    }
}
*/
