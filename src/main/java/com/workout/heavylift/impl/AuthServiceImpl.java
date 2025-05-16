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
//        // 실제 구현 시 refresh 토큰 검증 및 access 토큰 재발급 로직 필요
//        return jwtProvider.refreshToken(token);
        return "";
    }

    @Override
    public void logout(String token) {
        // 구현 예정: 블랙리스트 등록 또는 캐시 삭제 등
    }


}
