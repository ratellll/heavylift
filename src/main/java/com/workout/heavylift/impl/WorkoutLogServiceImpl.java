package com.workout.heavylift.impl;


import com.workout.heavylift.config.SecurityUtil;
import com.workout.heavylift.domain.Exercise;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkoutLogServiceImpl implements WorkoutLogService {

    private final WorkoutLogRepository workoutLogRepository;
    private final WorkoutLogExerciseRepository workoutLogExerciseRepository;
    private final UserRepository userRepository;
    private final ExerciseRepository exerciseRepository;


    @Override
    public WorkoutLogResponse createLog(CreateWorkoutLogRequest request) {
        Long currentUserId = SecurityUtil.getCurrentUserId();
        if (!currentUserId.equals(request.getUserId())) {
            throw new SecurityException("접근 권한이 없습니다.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        WorkoutLog log = WorkoutLog.builder()
                .user(user)
                .workoutDateTime(request.getWorkoutDateTime())
                .durationMinutes(request.getDurationMinutes())
                .caloriesBurned(request.getCaloriesBurned())
                .build();

        workoutLogRepository.save(log);

        List<WorkoutLogExercise> exercises = request.getLogExercises().stream().map(e -> {
            Exercise exercise = exerciseRepository.findById(e.getExerciseId())
                    .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));
            return WorkoutLogExercise.builder()
                    .workoutLog(log)
                    .exercise(exercise)
                    .sets(e.getSets())
                    .reps(e.getReps())
                    .weight(e.getWeight())
                    .build();
        }).collect(Collectors.toList());

        workoutLogExerciseRepository.saveAll(exercises);
        log.getLogExercises().addAll(exercises);

        return WorkoutLogResponse.fromEntity(log);
    }

    @Override
    public List<WorkoutLogResponse> getLogsByDate(LocalDate date) {
        Long userId = SecurityUtil.getCurrentUserId();
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();

        return workoutLogRepository.findByUserIdAndWorkoutDateTimeBetween(userId, start, end).stream()
                .peek(log -> log.getLogExercises().addAll(workoutLogExerciseRepository.findByWorkoutLog(log)))
                .map(WorkoutLogResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutLogResponse getLogById(Long logId) {
        WorkoutLog log = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new EntityNotFoundException("WorkoutLog not found"));
        log.getLogExercises().addAll(workoutLogExerciseRepository.findByWorkoutLog(log));
        return WorkoutLogResponse.fromEntity(log);
    }

    @Override
    public void deleteLog(Long logId) {
        if (!workoutLogRepository.existsById(logId)) {
            throw new EntityNotFoundException("WorkoutLog not found");
        }
        workoutLogRepository.deleteById(logId);
    }

    @Override
    public List<WorkoutLogResponse> getWeeklyStats() {
        Long userId = SecurityUtil.getCurrentUserId();
        LocalDateTime start = LocalDate.now().minusDays(6).atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();

        return workoutLogRepository.findByUserIdAndWorkoutDateTimeBetween(userId, start, end).stream()
                .peek(log -> log.getLogExercises().addAll(workoutLogExerciseRepository.findByWorkoutLog(log)))
                .map(WorkoutLogResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkoutLogResponse> getMonthlyStats() {
        Long userId = SecurityUtil.getCurrentUserId();
        LocalDateTime start = LocalDate.now().minusDays(29).atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();

        return workoutLogRepository.findByUserIdAndWorkoutDateTimeBetween(userId, start, end).stream()
                .peek(log -> log.getLogExercises().addAll(workoutLogExerciseRepository.findByWorkoutLog(log)))
                .map(WorkoutLogResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
