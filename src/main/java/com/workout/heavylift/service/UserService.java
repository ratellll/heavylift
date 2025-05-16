package com.workout.heavylift.service;

import com.workout.heavylift.dto.user.CreateUserRequest;
import com.workout.heavylift.dto.user.UpdateUserRequest;
import com.workout.heavylift.dto.user.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse getUser(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UpdateUserRequest request);
}
