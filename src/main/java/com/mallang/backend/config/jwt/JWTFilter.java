package com.mallang.backend.config.jwt;

import com.mallang.backend.domain.Member;
import com.mallang.backend.domain.Role;
import com.mallang.backend.config.CustomMemberDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);  // "Bearer " 부분 제거 후 토큰만 획득

        // 토큰 만료 확인
        if (jwtUtil.isExpired(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token expired");
            response.getWriter().flush();
            return;
        }

        // 토큰에서 사용자 정보 추출
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);  // 역할 추출

        // Member 객체 생성 및 역할 설정
        Member member = new Member();
        member.setMid(username);

        if ("ROLE_ADMIN".equals(role)) {
            member.changeRole(Role.ROLE_ADMIN);  // 관리자 역할 설정
        } else if ("ROLE_MEMBER".equals(role)) {
            member.changeRole(Role.ROLE_MEMBER);  // 일반 회원 역할 설정
        }

        // CustomMemberDetails를 사용해 인증 객체 생성
        CustomMemberDetails customUserDetails = new CustomMemberDetails(member);
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // SecurityContext에 설정
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }
}