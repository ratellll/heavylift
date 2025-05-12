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




}
