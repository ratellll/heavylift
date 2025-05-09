package com.workout.heavylift.config.jwt;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenExtractor {

    public String extraToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new IllegalArgumentException("Authorization 헤더가 필요하며, 'Bearer '로 시작해야 합니다.");
    }
    }
