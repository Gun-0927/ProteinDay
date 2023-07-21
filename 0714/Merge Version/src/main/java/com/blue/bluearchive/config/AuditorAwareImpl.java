    package com.blue.bluearchive.config;

    import com.blue.bluearchive.member.dto.CustomUserDetails;
    import org.springframework.data.domain.AuditorAware;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.context.SecurityContext;
    import org.springframework.security.core.context.SecurityContextHolder;

    import java.util.Optional;

    public class AuditorAwareImpl implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication auth = context.getAuthentication(); //간편회원가입시 loadUser 동장중 해당 값은 null 나옴

            String userId = ""; // 간편회원가입시 createdBy 는 공백으로 저장됨 자체회원가입 과 마찬가지로 필요하면 "anonymousUser"로 변경

            if (auth != null && auth.isAuthenticated()) { // 자체 회원가입시 loadUserByUsername 동작 중 principal 값은 null 이 아님
                Object principal = auth.getPrincipal();
                if (principal instanceof CustomUserDetails) {
                    CustomUserDetails userDetails = (CustomUserDetails) principal;
                    userId = Long.toString(userDetails.getIdx());
                } else if (principal instanceof String) {
                    userId = (String) principal; // 자체로그인 인증상태의 principal 내용 == anonymousUser
                }
            }

            return Optional.of(userId);
        }
    }
