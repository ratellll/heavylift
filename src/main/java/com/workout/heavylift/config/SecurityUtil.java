package com.workout.heavylift.config;

import com.workout.heavylift.config.jwt.JwtUserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtUserAuthentication jwtAuth) {
            return jwtAuth.getUserId();
        }

        throw new SecurityException("인증된 사용자 ID를 찾을 수 없습니다.");
    }
}
