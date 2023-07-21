package com.blue.bluearchive.member.entity;

import com.blue.bluearchive.constant.Grade;
import com.blue.bluearchive.constant.MemberStat;
import com.blue.bluearchive.constant.Role;
import com.blue.bluearchive.constant.ShopStatus;
import com.blue.bluearchive.shop.entity.BaseEntity;
import com.blue.bluearchive.member.dto.MemberFormDto;
import com.blue.bluearchive.userpage.entity.UserCategoryCount;
import com.blue.bluearchive.userpage.entity.UserCategoryLog;
import com.blue.bluearchive.userpage.entity.UserGraphLog;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="member")
@Getter @Setter
@ToString
public class Member extends BaseEntity {
    @Id
    @Column(name = "member_idx")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;

    private String id;

    private String name;

    private String nickName;

    @Column(unique=true)
    private String email;

    private String password;

    private String address;

    private String phoneNum;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Grade grade = Grade.BRONZE;

    @Enumerated(EnumType.STRING)
    private ShopStatus shopStatus = ShopStatus.CONSUMER;

    @Enumerated(EnumType.STRING)
    private MemberStat memberStat = MemberStat.MEMBER;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCategoryCount> userCategoryCount;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCategoryLog> userCategoryLog;
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserGraphLog> userGraphLog;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setId(memberFormDto.getId());
        String password=passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        member.setShopStatus(ShopStatus.CONSUMER);
        member.setNickName(memberFormDto.getNickName());
        member.setPhoneNum(memberFormDto.getPhoneNum());
        return member;
    }
}
