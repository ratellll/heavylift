package com.workout.heavylift.service;


import com.workout.heavylift.dto.user.CreateUserRequest;
import com.workout.heavylift.dto.user.UserResponse;

public interface AuthService {

    String login(String username, String password);
    UserResponse signup(CreateUserRequest request);
    String refreshToken(String token);
    void logout(String token);
}
