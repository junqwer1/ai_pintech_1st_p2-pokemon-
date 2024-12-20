package org.koreait.member.libs;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.util.json.JSONParserTokenManager;
import org.koreait.member.MemberInfo;
import org.koreait.member.constants.Authority;
import org.koreait.member.entities.Member;
import org.koreait.member.services.MemberInfoService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Setter
@Component
public class MemberUtil {

    private Member member;

    public boolean isLogin() {
        return getMember() != null;
    }

    /*
    * 관리자 여부
    *   권한 - MANAGER, ADMIN
    * */
    public boolean isAdmin() {
        return isLogin() &&
                getMember().getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority() == Authority.ADMIN || a.getAuthority() == Authority.MANAGER);
    }

    /*
    * 로그인한 회원의 정보 조회
    * */
    public Member getMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof MemberInfo memberInfo) {
            if (member == null) {
                setMember(memberInfo.getMember());

                return member;
            } else {
                return member;
            }
        }
        return null;
    }
}
