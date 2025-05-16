package com.workout.heavylift.controller;


import com.workout.heavylift.config.jwt.JwtProvider;
import com.workout.heavylift.domain.User;
import com.workout.heavylift.dto.user.CreateUserRequest;
import com.workout.heavylift.dto.user.UserResponse;
import com.workout.heavylift.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam @Email String email,
                                        @RequestParam @NotBlank String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            return ResponseEntity.badRequest().body("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        String token = jwtProvider.generateToken(user.get().getId());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        String encrypted = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encrypted)
                .build();
        userRepository.save(user);
        return ResponseEntity.ok(UserResponse.fromEntity(user));
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String oldToken) {
        String token = oldToken.replace("Bearer ", "");
        if (!jwtProvider.validateToken(token)) {
            return ResponseEntity.badRequest().body("유효하지 않은 토큰입니다.");
        }
        Long userId = jwtProvider.getUserIdFromToken(token);
        String newToken = jwtProvider.generateToken(userId);
        return ResponseEntity.ok(newToken);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        String rawToken = token.replace("Bearer ", "");
        long expiration = jwtProvider.getRemainingTime(rawToken);
        redisTemplate.opsForValue().set(rawToken, "logout", expiration, TimeUnit.MILLISECONDS);
        return ResponseEntity.ok().build();
    }
}
