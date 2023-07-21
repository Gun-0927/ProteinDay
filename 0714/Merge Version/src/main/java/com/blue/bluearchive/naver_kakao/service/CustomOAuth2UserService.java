package com.blue.bluearchive.naver_kakao.service;

import com.blue.bluearchive.constant.Role;
import com.blue.bluearchive.member.dto.CustomUserDetails;
import com.blue.bluearchive.member.entity.Member;
import com.blue.bluearchive.member.repository.MemberRepository;
import com.blue.bluearchive.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Transactional(readOnly = false)
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        System.out.println("내용들 출력 :           "+oAuth2User);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        String id = (String) response.get("id");
        String nickname = (String) response.get("nickname");
        String email = (String) response.get("email");
        String mobile = (String) response.get("mobile");
        String name = (String) response.get("name");


        // 사용자 정보가 이미 존재하는지 확인
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            // 사용자 정보가 없다면 새로운 사용자를 생성
            member = new Member();
            member.setName(name);
            member.setEmail(email);
            member.setAddress("테스트용주소");
            member.setId("간편로그인 테스트 ID");
            member.setPassword("password1234");
            member.setRole(Role.USER);
            member.setNickName(nickname);
            member.setPhoneNum(mobile);
            // 부족한 정보를 추가로 채워 넣는 코드를 여기에 작성
            member = memberService.saveMember(member);
        }

        // 사용자 정보를 가지고 UserDetails 객체를 생성
        CustomUserDetails userDetails = CustomUserDetails.builder()
                .idx(member.getIdx())
                .id(member.getId())
                .name(member.getName())
                .password(member.getPassword())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .address(member.getAddress())
                .phoneNum(member.getPhoneNum())
                .role(member.getRole())
                .grade(member.getGrade())
                .memberStat(member.getMemberStat())
                .createdBy(member.getCreatedBy())
                .attributes(oAuth2User.getAttributes())  // OAuth2User의 속성도 포함
                .build();

        return userDetails;
    }
}



//     기본 간편로그인시 사용
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        System.out.println("loadUser 진입!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        String provider = userRequest.getClientRegistration().getRegistrationId();
//        String providerId = oAuth2User.getAttribute("id");
//        String email = oAuth2User.getAttribute("email");
//        String name = oAuth2User.getAttribute("name");
//
//        System.out.println("=================================================");
//        System.out.println("Provider: " + provider);
//        System.out.println("ProviderId: " + providerId);
//        System.out.println("Email: " + email);
//        System.out.println("Name: " + name);
//
//        return oAuth2User;
//    }
//}

