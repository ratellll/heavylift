package com.workout.heavylift.service.impl;


import com.workout.heavylift.domain.User;
import com.workout.heavylift.dto.user.CreateUserRequest;
import com.workout.heavylift.dto.user.UpdateUserRequest;
import com.workout.heavylift.dto.user.UserResponse;
import com.workout.heavylift.exception.DuplicateEmailException;
import com.workout.heavylift.exception.UserNotFoundException;
import com.workout.heavylift.repository.UserRepository;
import com.workout.heavylift.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }
        User user = request.toEntity();
        // 비밀번호 암호화
        user.changeUserPassword(passwordEncoder.encode(request.getPassword()));
        User saved = userRepository.save(user);
        return UserResponse.fromEntity(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserResponse.fromEntity(user);
    }

    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // 이름 업데이트
        if (request.getName() != null) {
            user.changeUserName(request.getName());
        }
        // 닉네임 업데이트
        if (request.getNickName() != null) {
            user.changeUserNickName(request.getNickName());
        }
        // 이메일 업데이트 및 중복 검증
        if (request.getEmail() != null
                && !user.getEmail().equals(request.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateEmailException(request.getEmail());
            }
            user.changeUserEmail(request.getEmail());
        }
        // 비밀번호 업데이트
        if (request.getPassword() != null) {
            String encoded = passwordEncoder.encode(request.getPassword());
            user.changeUserPassword(encoded);
        }

        User updated = userRepository.save(user);
        return UserResponse.fromEntity(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
