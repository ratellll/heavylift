package com.workout.heavylift.service.impl;


import com.workout.heavylift.config.SecurityUtil;
import com.workout.heavylift.domain.Exercise;
import com.workout.heavylift.domain.RoutineExercise;
import com.workout.heavylift.domain.User;
import com.workout.heavylift.domain.WorkoutRoutine;
import com.workout.heavylift.dto.workoutroutine.CreateWorkoutRoutineRequest;
import com.workout.heavylift.dto.workoutroutine.RoutineExerciseDto;
import com.workout.heavylift.dto.workoutroutine.UpdateWorkoutRoutineRequest;
import com.workout.heavylift.dto.workoutroutine.WorkoutRoutineResponse;
import com.workout.heavylift.repository.ExerciseRepository;
import com.workout.heavylift.repository.RoutineExerciseRepository;
import com.workout.heavylift.repository.UserRepository;
import com.workout.heavylift.repository.WorkoutRoutineRepository;
import com.workout.heavylift.service.RoutineService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoutineServiceImpl implements RoutineService {

    private final WorkoutRoutineRepository routineRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    private final RoutineExerciseRepository routineExerciseRepository;

    @Override
    public WorkoutRoutineResponse createRoutine(CreateWorkoutRoutineRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저를 찾을 수 없습니다."));

        WorkoutRoutine routine = WorkoutRoutine.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .shared(request.isShared())
                .user(user)
                .build();

        routineRepository.save(routine);

        List<RoutineExercise> exercises = new ArrayList<>();
        for (RoutineExerciseDto dto : request.getExercises()) {
            Exercise exercise = exerciseRepository.findById(dto.getExerciseId())
                    .orElseThrow(() -> new EntityNotFoundException("운동을 찾을 수 없습니다."));
            RoutineExercise re = RoutineExercise.builder()
                    .exercise(exercise)
                    .routine(routine)
                    .sets(dto.getSets())
                    .reps(dto.getReps())
                    .weight(dto.getWeight())
                    .build();
            exercises.add(re);
        }
        routineExerciseRepository.saveAll(exercises);
        routine.setExercises(exercises);

        return WorkoutRoutineResponse.fromEntity(routine);
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutRoutineResponse getRoutine(Long routineId) {
        WorkoutRoutine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("루틴을 찾을 수 없습니다."));
        return WorkoutRoutineResponse.fromEntity(routine);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutRoutineResponse> getAllRoutines() {
        return routineRepository.findAllSharedWithExercises().stream()
                .map(WorkoutRoutineResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutRoutineResponse> getFavoriteRoutines() {
        Long userId = SecurityUtil.getCurrentUserId();
        return routineRepository.findFavoritesByUserId(userId).stream()
                .map(WorkoutRoutineResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutRoutineResponse updateRoutine(Long id, UpdateWorkoutRoutineRequest request) {
        WorkoutRoutine routine = routineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("루틴을 찾을 수 없습니다."));

        routine.update(request.getTitle(), request.getDescription(), request.isShared());

        List<RoutineExercise> exercises = new ArrayList<>();
        for (RoutineExerciseDto dto : request.getExercises()) {
            Exercise exercise = exerciseRepository.findById(dto.getExerciseId())
                    .orElseThrow(() -> new EntityNotFoundException("운동을 찾을 수 없습니다."));
            RoutineExercise re = RoutineExercise.builder()
                    .exercise(exercise)
                    .routine(routine)
                    .sets(dto.getSets())
                    .reps(dto.getReps())
                    .weight(dto.getWeight())
                    .build();
            exercises.add(re);
        }

        routine.setExercises(exercises);
        routineExerciseRepository.saveAll(exercises);

        return WorkoutRoutineResponse.fromEntity(routine);
    }

    @Override
    public void deleteRoutine(Long id) {
        WorkoutRoutine routine = routineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("루틴을 찾을 수 없습니다."));
        routineRepository.delete(routine);
    }

    @Override
    public void addToFavorites(Long routineId) {
        Long userId = SecurityUtil.getCurrentUserId();
        WorkoutRoutine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("루틴을 찾을 수 없습니다."));
        routineRepository.addFavorite(userId, routineId);
    }

    @Override
    public WorkoutRoutineResponse getRoutineTemplate() {
        // 샘플 템플릿 반환 로직
        WorkoutRoutine sample = WorkoutRoutine.builder()
                .title("기본 루틴 템플릿")
                .description("가슴/어깨/삼두 구성 예시")
                .shared(false)
                .exercises(Collections.emptyList())
                .build();
        return WorkoutRoutineResponse.fromEntity(sample);
    }
}

