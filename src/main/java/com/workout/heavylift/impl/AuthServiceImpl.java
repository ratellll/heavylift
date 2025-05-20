package com.workout.heavylift.impl;


import com.workout.heavylift.config.jwt.JwtProvider;
import com.workout.heavylift.domain.User;
import com.workout.heavylift.dto.user.CreateUserRequest;
import com.workout.heavylift.dto.user.UserResponse;
import com.workout.heavylift.repository.UserRepository;
import com.workout.heavylift.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지않습니다");
        }
        return jwtProvider.generateToken(user.getId());
    }

    @Override
    public UserResponse signup(CreateUserRequest request) {
            boolean exists = userRepository.existsByEmail(request.getEmail());
            if (exists) {
                throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
            }
        User user = User.builder()
                .name(request.getName())
                .nickName(request.getNickName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
            userRepository.save(user);


        return UserResponse.fromEntity(user);
    }

    @Override
    public String refreshToken(String token) {
        Long userId = jwtProvider.validateTokenAndGetUserId(token);
        return jwtProvider.generateToken(userId);
    }

    @Override
    public void logout(String token) {
        Long userId = jwtProvider.validateTokenAndGetUserId(token);
        Long expire = jwtProvider.getExpiration(token);
        redisTemplate.opsForValue().set(token, "logout", expire, TimeUnit.MILLISECONDS);
    }


}
