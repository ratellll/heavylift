package com.workout.heavylift.impl;


import com.workout.heavylift.domain.User;
import com.workout.heavylift.domain.WorkoutLog;
import com.workout.heavylift.domain.WorkoutLogExercise;
import com.workout.heavylift.dto.workoutlog.CreateWorkoutLogRequest;
import com.workout.heavylift.dto.workoutlog.WorkoutLogResponse;
import com.workout.heavylift.repository.ExerciseRepository;
import com.workout.heavylift.repository.UserRepository;
import com.workout.heavylift.repository.WorkoutLogExerciseRepository;
import com.workout.heavylift.repository.WorkoutLogRepository;
import com.workout.heavylift.service.WorkoutLogService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkoutLogServiceImpl implements WorkoutLogService{

    private final WorkoutLogRepository workoutLogRepository;
    private final WorkoutLogExerciseRepository workoutLogExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    @Override
    public WorkoutLogResponse createLog(CreateWorkoutLogRequest request) {
        Long userId = SecurityUtil.getCurrenUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("유저를 찾을수없습니다"));

        WorkoutLog log = WorkoutLog.builder()
                .user(user)
                .workoutDateTime(LocalDateTime.now())
                .
    }


}
