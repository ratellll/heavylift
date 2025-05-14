package com.workout.heavylift.impl;


import com.workout.heavylift.config.jwt.JwtProvider;
import com.workout.heavylift.repository.UserRepository;
import com.workout.heavylift.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;




}
