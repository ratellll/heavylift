package com.workout.heavylift.impl;


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
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을수없습니다"));

        WorkoutLog log = WorkoutLog.builder()
                .user(user)
                .workoutDateTime(request.getWorkoutDateTime())
                .durationMinutes(request.getDurationMinutes())
                .caloriesBurned(0)
                .build();

        workoutLogRepository.save(log);

        List<WorkoutLogExercise> exercises = request.getLogExercises().stream().map(e -> {
            Exercise exercise = exerciseRepository.findById(e.getExerciseId())
                    .orElseThrow(() -> new EntityNotFoundException("운동을 찾을수없습니다"));
            return WorkoutLogExercise.builder()
                    .workoutLog(log)
                    .exercise(exercise)
                    .sets(e.getSets())
                    .reps(e.getReps())
                    .weight(e.getWeight())
                    .build();
        }).collect(Collectors.toList());

        workoutLogExerciseRepository.saveAll(exercises);

        return WorkoutLogResponse.fromEntity(log);
    }

    @Override
    public List<WorkoutLogResponse> getLogsByDate(LocalDate date) {
        return workoutLogRepository.findAll().stream()
                .filter(l -> l.getWorkoutDateTime().toLocalDate().isEqual(date))
                .map(WorkoutLogResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutLogResponse getLogById(Long logId) {
        WorkoutLog log = workoutLogRepository.findById(logId)
                .orElseThrow(() -> new EntityNotFoundException("WorkoutLog not found"));
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
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(7);
        return workoutLogRepository.findAll().stream()
                .filter(log -> !log.getWorkoutDateTime().toLocalDate().isBefore(sevenDaysAgo))
                .map(WorkoutLogResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<WorkoutLogResponse> getMonthlyStats() {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        return workoutLogRepository.findAll().stream()
                .filter(log -> !log.getWorkoutDateTime().toLocalDate().isBefore(thirtyDaysAgo))
                .map(WorkoutLogResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
