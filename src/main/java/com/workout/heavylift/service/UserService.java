package com.workout.heavylift.service;

import com.workout.heavylift.dto.user.CreateUserRequest;
import com.workout.heavylift.dto.user.UpdateUserRequest;
import com.workout.heavylift.dto.user.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(CreateUserRequest request);

    UserResponse findUser(Long id);

    UserResponse updateUser(Long id,UpdateUserRequest request);

    void deleteUser(Long id);
    List<UserResponse> getAllUsers();

}
